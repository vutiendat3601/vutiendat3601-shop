package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface DistrictDao {
  @NonNull
  Page<District> selectAllByProvinceId(long provinceId, int page, int size);
}
