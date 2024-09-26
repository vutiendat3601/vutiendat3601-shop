package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface AddressDetailDao {
  @NonNull
  Page<AddressDetail> selectAllByCustomerId(long userId, int page, int size);
}
