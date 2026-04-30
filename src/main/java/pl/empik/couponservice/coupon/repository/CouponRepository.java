package pl.empik.couponservice.coupon.repository;

import org.springframework.data.repository.CrudRepository;
import pl.empik.couponservice.coupon.entity.CouponEntity;

public interface CouponRepository extends CrudRepository<CouponEntity, String> {
}
