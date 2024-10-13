package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface WardDetailDao {
  @NonNull
  Optional<WardDetail> selectById(long id);
}
