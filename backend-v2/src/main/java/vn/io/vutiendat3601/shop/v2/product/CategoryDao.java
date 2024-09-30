package vn.io.vutiendat3601.shop.v2.product;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface CategoryDao {
  @NonNull
  Page<Category> selectAll(int page, int size);
}
