package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface ProductDao {
  @NonNull
  Optional<Product> selectById(long id);

  @NonNull
  Page<Product> selectByCategoryCodeAndIsActiveTrue(@NonNull String categoryCode, int page, int size);

  @NonNull
  Optional<Product> selectByProductNoAndIsActiveTrue(@NonNull String productNo);
}
