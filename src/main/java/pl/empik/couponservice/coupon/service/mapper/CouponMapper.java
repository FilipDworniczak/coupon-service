package pl.empik.couponservice.coupon.service.mapper;

import pl.empik.couponservice.coupon.dto.CreateCouponRequest;
import pl.empik.couponservice.coupon.dto.CreateCouponResponse;
import pl.empik.couponservice.coupon.entity.CouponEntity;

public class CouponMapper {
    public static CouponEntity toEntity(CreateCouponRequest createCouponRequest) {
        var couponEntity = new CouponEntity();
        couponEntity.setCode(createCouponRequest.getCode());
        couponEntity.setUsageLimit(createCouponRequest.getUsageLimit());
        couponEntity.setTargetCountry(createCouponRequest.getTargetCountry());
        return couponEntity;
    }

    public static CreateCouponResponse toResponseDto(CouponEntity couponEntity) {
        var createCouponResponse = new CreateCouponResponse();
        createCouponResponse.setCode(couponEntity.getCode());
        createCouponResponse.setUsageLimit(couponEntity.getUsageLimit());
        createCouponResponse.setTargetCountry(couponEntity.getTargetCountry());
        return createCouponResponse;
    }
}
