package vn.io.vutiendat3601.shop.v2.customer;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface CustomerDao {
  @NonNull
  Optional<Customer> selectById(long id);

  boolean existsByUserId(long userId);

  void insert(@NonNull Customer customer);
}
