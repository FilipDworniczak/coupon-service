package pl.empik.couponservice.coupon.dto;

public interface CreateCoupon {
    String getCode();
    int getUsageLimit();
    String getTargetCountry();
}