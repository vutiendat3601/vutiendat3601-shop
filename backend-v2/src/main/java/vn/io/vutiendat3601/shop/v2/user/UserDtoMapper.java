package vn.io.vutiendat3601.shop.v2.user;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements Function<User, UserDto> {

  @Override
  public UserDto apply(User user) {
    if (user == null) {
      return null;
    }
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPhone(),
        user.getIsVerified());
  }
}
