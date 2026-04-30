package pl.empik.couponservice.coupon.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.empik.couponservice.coupon.dto.CreateCoupon;
import pl.empik.couponservice.coupon.dto.CreateCouponRequest;
import pl.empik.couponservice.coupon.dto.UseCouponResponse;
import pl.empik.couponservice.coupon.entity.CouponEntity;
import pl.empik.couponservice.coupon.repository.CouponRepository;
import pl.empik.couponservice.coupon.service.mapper.CouponMapper;
import pl.empik.couponservice.exception.CouponCountryNotAllowedException;
import pl.empik.couponservice.exception.CouponNotFoundException;
import pl.empik.couponservice.exception.CouponServiceException;
import pl.empik.couponservice.exception.CouponUsageLimitExceededException;
import pl.empik.couponservice.external.IpAddressService;

import java.util.Locale;

@Service
@Slf4j
@AllArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final IpAddressService ipAddressService;

    @Transactional
    public CreateCoupon create(CreateCouponRequest request) {
        log.info("Creating coupon: {}", request);
        var couponEntity = CouponMapper.toEntity(request);
        var savedEntity = couponRepository.save(couponEntity);
        log.info("Coupon has been created: {}", savedEntity);
        return CouponMapper.toResponseDto(savedEntity);
    }

    @Transactional
    public UseCouponResponse use(String code, String clientIp) {
        for (int i = 0; i < 3; i++) {
            try {
                return tryUse(code, clientIp);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.warn("Retrying due to concurrent update for code: {}", code);
            }
        }
        throw new CouponServiceException("Could not apply coupon due to concurrency. Coupon code: " + code);
    }

    private UseCouponResponse tryUse(String code, String clientIp) {
        log.info("Using coupon with code: {}", code);
        var coupon = couponRepository.findById(code.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found by code: " + code));
        validateCountry(coupon, clientIp);
        validateUsages(coupon);
        coupon.setUsageCount(coupon.getUsageCount() + 1);
        return new UseCouponResponse();
    }

    private void validateCountry(CouponEntity coupon, String clientIp) {
        var couponCountry = coupon.getTargetCountry();
        var clientCountry = ipAddressService.getCountryCodeByIp(clientIp);
        log.info("Comparing couponCountry: {} and clientCountry: {}", couponCountry, clientCountry);
        if (!Strings.CI.equals(couponCountry, clientCountry)) {
            throw new CouponCountryNotAllowedException("couponCountry: " + couponCountry + " doesn't match clientCountry: " + clientCountry);
        }
    }

    private void validateUsages(CouponEntity coupon) {
        var usageCount = coupon.getUsageCount();
        var usageLimit = coupon.getUsageLimit();
        if (usageCount >= usageLimit) {
            throw new CouponUsageLimitExceededException("Coupon has reached it's limit: " + usageLimit);
        }
    }
}
