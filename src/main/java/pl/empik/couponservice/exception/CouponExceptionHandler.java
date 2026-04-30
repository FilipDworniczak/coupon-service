package pl.empik.couponservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CouponExceptionHandler {
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<CouponError> handleCouponNotFoundException(CouponNotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(new CouponError(status.value(), e.getMessage()));
    }

    @ExceptionHandler(CouponCountryNotAllowedException.class)
    public ResponseEntity<CouponError> handleCouponCountryNotAllowedException(CouponCountryNotAllowedException e) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity
                .status(status)
                .body(new CouponError(status.value(), e.getMessage()));
    }

    @ExceptionHandler(CouponUsageLimitExceededException.class)
    public ResponseEntity<CouponError> handleCouponUsageLimitExceededException(CouponUsageLimitExceededException e) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity
                .status(status)
                .body(new CouponError(status.value(), e.getMessage()));
    }

    @ExceptionHandler(CouponAlreadyExistsException.class)
    public ResponseEntity<CouponError> handleCouponAlreadyExistsException(CouponAlreadyExistsException e) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(new CouponError(status.value(), e.getMessage()));
    }

    @ExceptionHandler(CouponServiceException.class)
    public ResponseEntity<CouponError> handleCouponServiceException(CouponServiceException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(new CouponError(status.value(), e.getMessage()));
    }
}
