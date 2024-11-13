package vn.io.vutiendat3601.shop.v2.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  private final UserService theTest;

  private final UserDao userDao;

  private final PasswordEncoder passwordEncoder;

  private final UserDtoMapper userDtoMapper = new UserDtoMapper();

  public UserServiceTest(@Mock UserDao userDao, @Mock PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
    theTest = new UserService(userDao, passwordEncoder, userDtoMapper);
  }

  @Test
  void couldCreateUser() {
    // Given
    final CreateUserRequest createUserReq =
        new CreateUserRequest("testuser", "test@gmail.com", "password123");
    when(userDao.existsByEmailOrUsername(createUserReq.email(), createUserReq.username()))
        .thenReturn(false);
    when(passwordEncoder.encode(createUserReq.password())).thenReturn("encodedPassword");

    // When
    theTest.createUser(createUserReq);

    // Then
    final User user =
        User.builder()
            .username(createUserReq.username())
            .email(createUserReq.email())
            .hashedPassword("encodedPassword")
            .authorities(List.of("ROLE_USER"))
            .build();

    verify(userDao, times(1))
        .existsByEmailOrUsername(createUserReq.email(), createUserReq.username());
    verify(passwordEncoder, times(1)).encode(createUserReq.password());
  }

  @Test
  void couldThrowConflictExceptionIfUserExists() {
    // Given
    final CreateUserRequest createUserReq =
        new CreateUserRequest("testuser", "test@example.com", "password123");
    when(userDao.existsByEmailOrUsername(createUserReq.email(), createUserReq.username()))
        .thenReturn(true);

    // When & Then
    final ConflictException exception =
        assertThrows(
            ConflictException.class,
            () -> {
              theTest.createUser(createUserReq);
            });
    assertEquals("Email or username was already taken", exception.getMessage());
    verify(userDao, times(1))
        .existsByEmailOrUsername(createUserReq.email(), createUserReq.username());
  }

  @Test
  void couldReturnUserDtoWhenUserExists() {
    // Given
    final String username = "existingUser";
    final User user =
        User.builder()
            .username(username)
            .email("existingUser@example.com")
            .hashedPassword("hashedPassword")
            .authorities(List.of("ROLE_USER"))
            .build();
    when(userDao.selectByUsername(username)).thenReturn(Optional.of(user));

    // When
    final UserDto userDto = theTest.getUserByUsername(username);

    // Then
    assertEquals(user.getUsername(), userDto.username());
    assertEquals(user.getEmail(), userDto.email());
  }

  @Test
  void couldThrowResourceNotFoundExceptionWhenUserNotFound() {
    // Given
    final String username = "nonExistingUser";
    when(userDao.selectByUsername(username)).thenReturn(Optional.empty());

    // When & Then
    final ResourceNotFoundException exception =
        assertThrows(ResourceNotFoundException.class, () -> theTest.getUserByUsername(username));
    assertEquals("User not found", exception.getMessage());
  }
}
