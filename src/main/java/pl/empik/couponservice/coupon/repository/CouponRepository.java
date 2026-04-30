package pl.empik.couponservice.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.empik.couponservice.coupon.entity.CouponEntity;

public interface CouponRepository extends JpaRepository<CouponEntity, String> {
}
