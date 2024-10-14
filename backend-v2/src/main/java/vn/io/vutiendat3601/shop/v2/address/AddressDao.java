package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface AddressDao {
  long insert(@NonNull Address addr);

  @NonNull
  Optional<Address> selectById(long id);

  @NonNull
  Optional<Address> selectByCodeAndCustomerCode(@NonNull String code, @NonNull String customerCode);
}
