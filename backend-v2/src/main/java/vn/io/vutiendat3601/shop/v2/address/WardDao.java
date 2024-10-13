package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface WardDao {
  @NonNull
  Page<Ward> selectAllByDistrictId(long distId, int page, int size);

  boolean existsById(long wardId);

  @NonNull
  Optional<Ward> selectById(long id);
}
