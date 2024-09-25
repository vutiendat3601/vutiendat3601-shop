package vn.io.vutiendat3601.shop.v2.verification;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

public interface VerificationDao {
  @NonNull
  Optional<Verification> selectByCodeAndType(@NonNull String code, @NonNull VerificationType type);

  @NonNull
  List<Verification> selectAllByUserIdAndTypeAndIsDisabled(long userId, @NonNull VerificationType type, boolean isDisabled);

  @NonNull
  Optional<Verification> selectFirstByUserIdAndTypeOrderByCreatedAtDesc(
      long userId, @NonNull VerificationType type);

  void update(@NonNull List<Verification> verifs);

  void update(@NonNull Verification verif);

  void insert(@NonNull Verification verif);
}
