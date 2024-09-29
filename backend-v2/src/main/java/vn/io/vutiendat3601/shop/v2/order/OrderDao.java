package vn.io.vutiendat3601.shop.v2.order;

import org.springframework.lang.NonNull;

public interface OrderDao {
  void insert(@NonNull Order order);
}
