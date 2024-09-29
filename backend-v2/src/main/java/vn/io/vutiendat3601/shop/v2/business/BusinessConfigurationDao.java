package vn.io.vutiendat3601.shop.v2.business;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface BusinessConfigurationDao {
  @NonNull
  Optional<BusinessConfiguration> selectByKeyAndValueType(
      @NonNull String key, @NonNull BusinessConfigurationValueType type);
}
