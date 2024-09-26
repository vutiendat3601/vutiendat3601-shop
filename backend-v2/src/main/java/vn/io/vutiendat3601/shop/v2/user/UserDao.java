package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface UserDao {
  @NonNull
  Optional<User> selectById(@NonNull Long id);

  @NonNull
  Optional<User> selectByEmail(@NonNull String email);

  @NonNull
  Optional<User> selectByUsername(@NonNull String username);

  @NonNull
  Optional<User> selectByPhone(@NonNull String phone);

  void insert(@NonNull User user);

  boolean existsByEmailOrUsername(@NonNull String email, @NonNull String username);
}
