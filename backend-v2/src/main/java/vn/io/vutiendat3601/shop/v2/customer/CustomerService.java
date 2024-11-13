package vn.io.vutiendat3601.shop.v2.customer;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserDao;

@RequiredArgsConstructor
@Service
public class CustomerService {
  private final CustomerDao customerDao;
  private final UserDao userDao;

  public void createCustomer(@NonNull CreateCustomerRequest createCustomerReq) {
    final long userId = createCustomerReq.userId();
    final User user =
        userDao
            .selectById(createCustomerReq.userId())
            .orElseThrow(
                () -> new ResourceNotFoundException("User not found: (id=%d)".formatted(userId)));
    final boolean isExisted = customerDao.existsByUserId(createCustomerReq.userId());
    if (isExisted) {
      throw new ConflictException("Customer with userId already exists");
    }
    final Customer customer =
        Customer.builder()
            .code(UUID.randomUUID().toString())
            .name(createCustomerReq.name())
            .phones(createCustomerReq.phones())
            .user(user)
            .build();
    customerDao.insert(customer);
  }
}
