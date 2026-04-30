package pl.empik.couponservice.coupon.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.empik.couponservice.coupon.dto.CreateCouponRequest;
import pl.empik.couponservice.coupon.entity.CouponEntity;
import pl.empik.couponservice.coupon.repository.CouponRepository;
import pl.empik.couponservice.exception.CouponCountryNotAllowedException;
import pl.empik.couponservice.exception.CouponNotFoundException;
import pl.empik.couponservice.external.IpAddressService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    public static final String WIOSNA = "WIOSNA";
    @Mock
    private CouponRepository couponRepository;

    @Mock
    private IpAddressService ipAddressService;

    @InjectMocks
    private CouponService couponService;

    @Test
    public void create_whenNotExists() {
        //given
        CreateCouponRequest request = buildCreateCouponRequest();

        when(couponRepository.findById(WIOSNA)).thenReturn(Optional.empty());
        when(couponRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        //when
        var result = couponService.create(request);

        //then
        assertNotNull(result);
        verify(couponRepository).save(any());
    }

    @Test
    void use_shouldThrowCouponNotFoundExceptionWhenCouponNotFound() {
        when(couponRepository.findById(WIOSNA)).thenReturn(Optional.empty());

        assertThrows(CouponNotFoundException.class,
                () -> couponService.use(WIOSNA, "1.1.1.1"));
    }

    @Test
    void shouldThrow_whenCountryDoesNotMatch() {
        when(couponRepository.findById(WIOSNA)).thenReturn(Optional.of(buildCouponEntity()));
        when(ipAddressService.getCountryCodeByIp("1.1.1.1")).thenReturn("DE");

        assertThrows(CouponCountryNotAllowedException.class,
                () -> couponService.use(WIOSNA, "1.1.1.1"));
    }

    private CreateCouponRequest buildCreateCouponRequest() {
        var coupon = new CreateCouponRequest();
        coupon.setCode(WIOSNA);
        coupon.setUsageLimit(5);
        coupon.setTargetCountry("PL");
        return coupon;
    }

    private CouponEntity buildCouponEntity() {
        var coupon = new CouponEntity();
        coupon.setCode(WIOSNA);
        coupon.setUsageLimit(5);
        coupon.setUsageCount(0);
        coupon.setTargetCountry("PL");
        return coupon;
    }
}