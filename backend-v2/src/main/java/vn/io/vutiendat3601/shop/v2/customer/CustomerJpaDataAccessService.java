package vn.io.vutiendat3601.shop.v2.customer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomerJpaDataAccessService implements CustomerDao {
  private final CustomerRepository customerRepo;

  @Override
  @NonNull
  public Optional<Customer> selectById(long id) {
    return customerRepo.findById(id);
  }

  @Override
  public boolean existsByUserId(long userId) {
    return customerRepo.existsByUserId(userId);
  }

  @Override
  public void insert(@NonNull Customer customer) {
    customerRepo.save(customer);
  }

  @Override
  @NonNull
  public Optional<Customer> selectByCode(@NonNull String code) {
    return customerRepo.findByCode(code);
  }
}
