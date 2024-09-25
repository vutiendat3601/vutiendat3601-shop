package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User, Long> {
  @NonNull
  Optional<User> findByEmail(@NonNull String email);

  @NonNull
  Optional<User> findByPhone(@NonNull String Phone);

  boolean existsByEmail(@NotNull String email);
}
