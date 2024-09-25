package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

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
  public boolean existsByEmail(@NotNull String email) {
    return userRepo.existsByEmail(email);
  }
}
