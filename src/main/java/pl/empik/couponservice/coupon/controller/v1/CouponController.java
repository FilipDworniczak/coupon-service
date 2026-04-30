package pl.empik.couponservice.coupon.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.empik.couponservice.coupon.dto.CreateCoupon;
import pl.empik.couponservice.coupon.dto.CreateCouponRequest;
import pl.empik.couponservice.coupon.dto.UseCouponResponse;
import pl.empik.couponservice.coupon.entity.CouponEntity;
import pl.empik.couponservice.coupon.service.CouponService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
@Tag(name = "Coupon API", description = "Endpoints for handling Coupons")
@AllArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "Create coupon", description = "Create coupon defined by: code, usageLimit and targetCountry")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCoupon> create(@Valid @RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(couponService.create(request));
    }

    @Operation(summary = "Use coupon")
    @PostMapping("/use/{code}")
    public ResponseEntity<UseCouponResponse> use(
            @Parameter(required = true, name = "code", description = "Coupon code", in = ParameterIn.PATH)
            @PathVariable String code,
            @RequestAttribute("clientIp") String clientIp) {
        return ResponseEntity.ok(couponService.use(code, clientIp));
    }

    @Operation(summary = "Get all coupons", description = "For test purpose only: get all coupons to verify data state")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CouponEntity>> getAll() {
        return ResponseEntity.ok(couponService.getAll());
    }
}
