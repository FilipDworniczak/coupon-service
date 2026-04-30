package pl.empik.couponservice.coupon.dto;

import lombok.Data;

@Data
public class CreateCouponResponse implements CreateCoupon {
    private String code;
    private int usageLimit;
    private String targetCountry;
}
