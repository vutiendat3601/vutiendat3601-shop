package vn.io.vutiendat3601.shop.v2.user;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Long> {
  @NonNull
  Optional<User> findByEmail(@NonNull String email);

  @NonNull
  Optional<User> findByUsername(@NonNull String username);

  @NonNull
  Optional<User> findByPhone(@NonNull String Phone);

  boolean existsByEmailOrUsername(@NotNull String email, @NotNull String username);
}
