package vn.io.vutiendat3601.shop.v2.user;

import static vn.io.vutiendat3601.shop.v2.security.SecurityConstant.ROLE_USER;

import java.util.List;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.common.ConflictException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserDao userDao;
  private final PasswordEncoder passEncoder;

  public void createUser(CreateUserRequest createUserReq) {
    final boolean isExisted = userDao.existsByEmail(createUserReq.email());
    if (isExisted) {
      throw new ConflictException("Email was already taken");
    }
    final List<String> roles = List.of(ROLE_USER);
    final User user =
        User.builder()
            .displayName(createUserReq.displayName())
            .email(createUserReq.email())
            .hashedPassword(passEncoder.encode(createUserReq.password()))
            .roles(roles)
            .build();
    userDao.insert(user);
  }
}
