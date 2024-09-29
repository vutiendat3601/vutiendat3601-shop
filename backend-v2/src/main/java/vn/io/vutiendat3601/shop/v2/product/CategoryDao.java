package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface CategoryDao {
  @NonNull
  List<Category> selectAll();

  @NonNull
  Optional<Category> selectById(long id);

  void insertCategory(Category category);

  void updateCategory(Category category);

  void deleteCategory(Long id);

  boolean existsCategoryByCode(String code);
}
