package vn.io.vutiendat3601.shop.v2.category;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface CategoryDao {
  @NonNull
  Optional<Category> selectById(long id);

  List<Category> selectAllCategories();
}
