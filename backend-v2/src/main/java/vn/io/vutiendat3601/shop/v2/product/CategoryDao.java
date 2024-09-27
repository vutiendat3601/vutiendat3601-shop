package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import org.springframework.lang.NonNull;

public interface CategoryDao {
  @NonNull
  List<Category> selectAll();
}
