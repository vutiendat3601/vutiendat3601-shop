package vn.io.vutiendat3601.shop.v2.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import vn.io.vutiendat3601.shop.v2.config.TestConfig;
import vn.io.vutiendat3601.shop.v2.testcontainers.AbstractTestcontainersTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class UserRepositoryTest extends AbstractTestcontainersTest {
  private UserRepository userRepo;

  @Autowired
  public UserRepositoryTest(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Test
  void foundUserByEmail() {
    // Given
    final User newUser =
        User.builder()
            .username(FAKER.internet().username())
            .authorities(List.of("ROLE_USER"))
            .email(FAKER.internet().emailAddress())
            .build();

    // When
    userRepo.save(newUser);
    final Optional<User> actual = userRepo.findByEmail(newUser.getEmail());

    // Then
    assertTrue(actual.isPresent());
    final User actualUser = actual.get();
    assertEquals(newUser.getEmail(), actualUser.getEmail());
  }

  @Test
  void foundUserByUsername() {
    // Given
    final User newUser =
        User.builder()
            .username(FAKER.internet().username())
            .authorities(List.of("ROLE_USER"))
            .email(FAKER.internet().emailAddress())
            .build();

    // When
    userRepo.save(newUser);
    final Optional<User> actual = userRepo.findByUsername(newUser.getUsername());

    // Then
    assertTrue(actual.isPresent());
    final User actualUser = actual.get();
    assertEquals(newUser.getUsername(), actualUser.getUsername());
  }

  @Test
  void foundUserByPhone() {
    // Given
    final User newUser =
        User.builder()
            .username(FAKER.internet().username())
            .authorities(List.of("ROLE_USER"))
            .email(FAKER.internet().emailAddress())
            .phone("0123456789")
            .build();

    // When
    userRepo.save(newUser);
    final Optional<User> actual = userRepo.findByPhone(newUser.getPhone());

    // Then
    assertTrue(actual.isPresent());
    final User actualUser = actual.get();
    assertEquals(newUser.getPhone(), actualUser.getPhone());
  }

  @Test
  void foundUserExistsByEmailOrUsernmae() {
    // Given
    final User newUser =
        User.builder()
            .username("01234567890")
            .authorities(List.of("ROLE_USER"))
            .email("test@gmail.com")
            .build();

    // When
    userRepo.save(newUser);
    boolean actual = userRepo.existsByEmailOrUsername(newUser.getEmail(), newUser.getUsername());
    boolean actualWithoutEmail = userRepo.existsByEmailOrUsername(null, newUser.getUsername());
    boolean actualWithoutUsername = userRepo.existsByEmailOrUsername(newUser.getEmail(), null);

    // Then
    assertTrue(actual);
    assertTrue(actualWithoutEmail);
    assertTrue(actualWithoutUsername);
  }
}
