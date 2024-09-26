package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface ProductDao {
  @NonNull
  Optional<Product> selectById(long id);

  @NonNull
  List<Product> selectByCategoryId(long categoryId);

  List<Product> selectAllProduct();
}
