package vn.io.vutiendat3601.shop.v2.user;

import static vn.io.vutiendat3601.shop.v2.security.SecurityConstant.ROLE_USER;

import java.util.List;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserDao userDao;
  private final PasswordEncoder passEncoder;
  private final UserDtoMapper userDtoMapper;

  public void createUser(CreateUserRequest createUserReq) {
    boolean isExisted =
        userDao.existsByEmailOrUsername(createUserReq.email(), createUserReq.username());
    if (isExisted) {
      throw new ConflictException("Email or username was already taken");
    }
    final List<String> authorities = List.of(ROLE_USER);
    final User user =
        User.builder()
            .username(createUserReq.username())
            .email(createUserReq.email())
            .hashedPassword(passEncoder.encode(createUserReq.password()))
            .authorities(authorities)
            .build();
    userDao.insert(user);
  }

  public UserDto getUserByUsername(String username) {
    final User user =
        userDao
            .selectByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    return userDtoMapper.apply(user);
  }
}
