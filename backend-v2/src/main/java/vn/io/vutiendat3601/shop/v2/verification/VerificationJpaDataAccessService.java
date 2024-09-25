package vn.io.vutiendat3601.shop.v2.verification;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VerificationJpaDataAccessService implements VerificationDao {
  private final VerificationRepository verifRepo;

  @Override
  @NonNull
  public Optional<Verification> selectByCodeAndType(
      @NonNull String code, @NonNull VerificationType type) {
    return verifRepo.findByCodeAndType(code, type);
  }

  @Override
  @NonNull
  public List<Verification> selectAllByUserIdAndTypeAndIsDisabled(
      long userId, @NonNull VerificationType type, boolean isDisabled) {

    return verifRepo.findAllByUserIdAndTypeAndIsDisabled(userId, type, isDisabled);
  }

  @Override
  public void update(@NonNull Verification verif) {
    verifRepo.save(verif);
  }

  @Override
  public void update(@NonNull List<Verification> verifs) {
    verifRepo.saveAll(verifs);
  }

  @Override
  public void insert(@NonNull Verification verification) {
    verifRepo.save(verification);
  }

  @Override
  @NonNull
  public Optional<Verification> selectFirstByUserIdAndTypeOrderByCreatedAtDesc(
      long userId, @NonNull VerificationType type) {
    return verifRepo.findFirstByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
  }
}
