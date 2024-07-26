package vn.io.vutiendat3601.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.io.vutiendat3601.shop.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
