package vn.io.vutiendat3601.shop.v2.customer;

import static org.mockito.Mockito.verify;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.io.vutiendat3601.shop.v2.user.User;

@ExtendWith(MockitoExtension.class)
public class CustomerJpaDataAccessServiceTest {
  private CustomerJpaDataAccessService theTest;

  private final CustomerRepository customerRepo;

  public CustomerJpaDataAccessServiceTest(@Mock CustomerRepository customerRepo) {
    this.customerRepo = customerRepo;
    theTest = new CustomerJpaDataAccessService(customerRepo);
  }

  @Test
  void verifyExistsByUserId() {
    // Given
    final Long userId = FAKER.random().nextLong();

    // When
    theTest.existsByUserId(userId);

    // Then
    verify(customerRepo).existsByUserId(userId);
  }

  @Test
  void verifyInsert() {
    // Given
    final User user =
        User.builder()
            .id(1L)
            .username(FAKER.internet().username())
            .authorities(List.of("ROLE_USER"))
            .phone("079788234123")
            .email("test@gmail.com")
            .build();
    final Customer newCustomer = Customer.builder().user(user).build();

    // When
    theTest.insert(newCustomer);

    // Then
    verify(customerRepo).save(newCustomer);
  }

  @Test
  void verifySelectByCode() {
    // Given
    final String code = UUID.randomUUID().toString();

    // When
    theTest.selectByCode(code);

    // Then
    verify(customerRepo).findByCode(code);
  }

  @Test
  void verifySelectById() {
    // Given
    final long id = FAKER.random().nextLong();

    // When
    theTest.selectById(id);

    // Then
    verify(customerRepo).findById(id);
  }
}
