package pl.empik.couponservice.exception;

public class CouponCountryNotAllowedException extends RuntimeException {
    public CouponCountryNotAllowedException(String message) {
        super(message);
    }
}
