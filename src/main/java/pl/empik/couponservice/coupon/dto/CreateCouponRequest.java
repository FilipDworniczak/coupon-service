package pl.empik.couponservice.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCouponRequest implements CreateCoupon {
    @Schema(description = "Case-insensitive coupon code", requiredMode = Schema.RequiredMode.REQUIRED, example = "WIOSNA")
    @NotBlank(message = "code cannot be blank")
    private String code;

    @Schema(description = "Coupon usage limit", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    @NotNull(message = "usageLimit cannot be null")
    @DecimalMin(value = "0", inclusive = false, message = "Usage Limit Must be greater than 0")
    private int usageLimit;

    @Schema(description = "Country code in ISO 3166-1 alpha-2. Users outside provided country won't be able to use the coupon", requiredMode = Schema.RequiredMode.REQUIRED, example = "PL")
    @NotNull(message = "targetCountry cannot be null")
    @Size(min = 2, max = 2, message = "targetCountry must be a 2-letter uppercase string")
    @Pattern(regexp = "^[A-Z]{2}$", message = "targetCountry must be a 2-letter uppercase string")
    private String targetCountry;
}
