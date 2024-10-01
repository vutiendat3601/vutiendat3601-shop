package vn.io.vutiendat3601.shop.v2.business;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface BusinessConfigurationRepository
    extends JpaRepository<BusinessConfiguration, Long> {
  @NonNull
  Optional<BusinessConfiguration> findByKeyAndValueType(
      @NonNull String key, @NonNull BusinessConfigurationValueType valueType);
}
