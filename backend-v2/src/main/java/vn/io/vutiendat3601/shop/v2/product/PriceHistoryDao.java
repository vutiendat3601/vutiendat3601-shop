package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import org.springframework.lang.NonNull;

public interface PriceHistoryDao {
  @NonNull
  List<PriceHistory> selectByProductId(long productId, int page, int size);

  void insert(@NonNull PriceHistory priceHistory);
}
