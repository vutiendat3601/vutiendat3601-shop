package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJpaDataAccessService implements UserDao {
  private final UserRepository userRepo;

  @NonNull
  @Override
  public Optional<User> selectById(@NonNull Long id) {
    return userRepo.findById(id);
  }

  @NonNull
  @Override
  public Optional<User> selectByEmail(@NonNull String email) {
    return userRepo.findByEmail(email);
  }

  @Override
  @NonNull
  public Optional<User> selectByUsername(@NonNull String username) {
    return userRepo.findByUsername(username);
  }

  @NonNull
  @Override
  public Optional<User> selectByPhone(@NonNull String phone) {
    return userRepo.findByPhone(phone);
  }

  @Override
  public void insert(@NonNull User user) {
    userRepo.save(user);
  }

  @Override
  public boolean existsByEmailOrUsername(@NonNull String email, @NonNull String username) {
    return userRepo.existsByEmailOrUsername(email, username);
  }
}
