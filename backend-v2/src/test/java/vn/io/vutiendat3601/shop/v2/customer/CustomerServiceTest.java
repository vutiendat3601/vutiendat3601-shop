package vn.io.vutiendat3601.shop.v2.customer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserDao;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
  private final CustomerService theTest;

  private final CustomerDao customerDao;

  private final UserDao userDao;

  public CustomerServiceTest(@Mock CustomerDao customerDao, @Mock UserDao userDao) {
    this.customerDao = customerDao;
    this.userDao = userDao;
    theTest = new CustomerService(customerDao, userDao);
  }

  @Test
  void couldCreateCustomer() {
    // Given
    final User user =
        User.builder()
            .id(FAKER.random().nextLong(1, Long.MAX_VALUE))
            .email("test@gmail.com")
            .phone(FAKER.phoneNumber().cellPhone())
            .username(FAKER.internet().username())
            .build();
    final CreateCustomerRequest createCustomerReq =
        new CreateCustomerRequest(user.getId(), "Dat Vu", List.of(FAKER.phoneNumber().cellPhone()));
    when(userDao.selectById(createCustomerReq.userId())).thenReturn(Optional.of(user));
    when(customerDao.existsByUserId(user.getId())).thenReturn(false);

    // When
    theTest.createCustomer(createCustomerReq);

    // Then
    final ArgumentCaptor<Customer> newCustomerCapture = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).insert(newCustomerCapture.capture());
    final Customer newCustomer = newCustomerCapture.getValue();
    assertEquals(createCustomerReq.userId(), newCustomer.getUser().getId());
    assertEquals(createCustomerReq.name(), newCustomer.getName());
    assertEquals(createCustomerReq.phones(), newCustomer.getPhones());
  }
}
