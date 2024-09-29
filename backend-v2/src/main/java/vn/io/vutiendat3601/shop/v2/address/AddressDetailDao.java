package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface AddressDetailDao {
  @NonNull
  Page<AddressDetail> selectAllByCustomerId(long userId, int page, int size);

  @NonNull
  Optional<AddressDetail> selectById(@NonNull Long id);

  @NonNull
  Optional<AddressDetail> selectByCodeAndCustomerCode(
      @NonNull String code, @NonNull String customerCode);
}
