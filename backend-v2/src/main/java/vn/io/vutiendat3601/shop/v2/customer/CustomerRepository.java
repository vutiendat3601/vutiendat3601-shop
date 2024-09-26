package vn.io.vutiendat3601.shop.v2.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  boolean existsByUserId(long userId);
}
