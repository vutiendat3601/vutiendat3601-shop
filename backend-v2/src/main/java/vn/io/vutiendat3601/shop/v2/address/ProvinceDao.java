package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;

public interface ProvinceDao {
  Page<Province> selectAll(int page, int size);
}
