package vn.io.vutiendat3601.shop.v2.business;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BusinessConfigurationJpaDataAccessService implements BusinessConfigurationDao {
  private final BusinessConfigurationRepository businessConfigRepo;

  @Override
  @NonNull
  public Optional<BusinessConfiguration> selectByKeyAndValueType(
      @NonNull String key, @NonNull BusinessConfigurationValueType type) {
    return businessConfigRepo.findByKeyAndValueType(key, type);
  }
}
