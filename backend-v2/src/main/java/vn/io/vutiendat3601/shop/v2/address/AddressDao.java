package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.lang.NonNull;

public interface AddressDao {
  void insert(@NonNull Address addr);
}
