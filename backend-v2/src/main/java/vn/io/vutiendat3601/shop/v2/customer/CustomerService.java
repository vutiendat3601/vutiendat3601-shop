package vn.io.vutiendat3601.shop.v2.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.user.User;

@RequiredArgsConstructor
@Service
public class CustomerService {
  private final CustomerDao customerDao;

  public void createCustomer(@NonNull CreateCustomerRequest createCustomerReq) {
    final boolean isExisted = customerDao.existsByUserId(createCustomerReq.userId());
    if (isExisted) {
      throw new ConflictException("Customer with userId already exists");
    }
    final Customer customer =
        Customer.builder()
            .name(createCustomerReq.name())
            .phones(createCustomerReq.phones())
            .user(new User(createCustomerReq.userId()))
            .build();
    customerDao.insert(customer);
  }
}
