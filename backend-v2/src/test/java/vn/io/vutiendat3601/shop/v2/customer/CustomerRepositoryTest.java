package vn.io.vutiendat3601.shop.v2.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import vn.io.vutiendat3601.shop.v2.config.TestConfig;
import vn.io.vutiendat3601.shop.v2.testcontainers.AbstractTestcontainersTest;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class CustomerRepositoryTest extends AbstractTestcontainersTest {
  private CustomerRepository customerRepo;
  private UserRepository userRepo;

  @Autowired
  public CustomerRepositoryTest(CustomerRepository customerRepo, UserRepository userRepo) {
    this.customerRepo = customerRepo;
    this.userRepo = userRepo;
  }

  @Test
  void foundCustomerByCode() {
    // Given
    final User newUser =
        User.builder()
            .username(FAKER.internet().username())
            .authorities(List.of("ROLE_USER"))
            .email(FAKER.internet().emailAddress())
            .build();
    final Customer newCustomer =
        Customer.builder()
            .code(UUID.randomUUID().toString())
            .name(FAKER.funnyName().name())
            .phones(List.of(FAKER.phoneNumber().cellPhone()))
            .build();

    // When
    final User user = userRepo.save(newUser);
    newCustomer.setUser(user);
    customerRepo.save(newCustomer);
    final Optional<Customer> actual = customerRepo.findByCode(newCustomer.getCode());

    // Then
    assertTrue(actual.isPresent());
    final Customer actualCustomer = actual.get();
    assertEquals(newCustomer.getName(), actualCustomer.getName());
    assertEquals(newCustomer.getCode(), actualCustomer.getCode());
  }
}
