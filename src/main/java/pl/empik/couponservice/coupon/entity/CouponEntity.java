package pl.empik.couponservice.coupon.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "COUPON")
@Data
public class CouponEntity {
    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "USAGE_LIMIT", nullable = false, updatable = false)
    private int usageLimit;

    @Column(name = "USAGE_COUNT", nullable = false)
    private int usageCount = 0;

    @Column(name = "TARGET_COUNTRY")
    private String targetCountry;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private Long version;
}
