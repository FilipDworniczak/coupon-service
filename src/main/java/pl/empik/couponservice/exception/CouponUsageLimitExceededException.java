package pl.empik.couponservice.exception;

public class CouponUsageLimitExceededException extends RuntimeException {
    public CouponUsageLimitExceededException(String message) {
        super(message);
    }
}
