package vn.io.vutiendat3601.shop.v2.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  boolean existsByUserId(long userId);

  @NonNull
  Optional<Customer> findByCode(@NonNull String code);
}
