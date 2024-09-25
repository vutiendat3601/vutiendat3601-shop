package vn.io.vutiendat3601.shop.v2.verification;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
  @NonNull
  Optional<Verification> findByCodeAndType(@NonNull String code, @NonNull VerificationType type);

  @NonNull
  List<Verification> findAllByUserIdAndTypeAndIsDisabled(
      long userId, @NonNull VerificationType type, boolean isDisabled);

  @NonNull
  Optional<Verification> findFirstByUserIdAndTypeOrderByCreatedAtDesc(
      long userId, @NonNull VerificationType type);
}
