package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface CategoryDao {

  @NonNull
  Optional<Category> selectById(long id);

  void insertCategory(Category category);

  void updateCategory(Category category);

  void deleteCategory(Long id);

  boolean existsCategoryByCode(String code);

  Page<Category> selectAll(int page, int size);
}
