package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface AddressDao {
  void insert(@NonNull Address addr);

  @NonNull
  Optional<Address> selectByCodeAndCustomerCode(@NonNull String code, @NonNull String customerCode);
}
