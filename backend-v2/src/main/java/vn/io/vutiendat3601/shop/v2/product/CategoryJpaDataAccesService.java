package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryJpaDataAccesService implements CategoryDao {
  private final CategoryRepository categoryRespo;

  @Override
  @NonNull
  public List<Category> selectAll() {
    return categoryRespo.findAll();
  }

  @Override
  @NonNull
  public Optional<Category> selectById(long id) {
    return categoryRespo.findById(id);
  }

  @Override
  public void insertCategory(Category category) {
    categoryRespo.save(category);
  }

  @Override
  public boolean existsCategoryByCode(String code) {
    return categoryRespo.existsCategoryByCode(code);
  }

  @Override
  public void updateCategory(Category category) {
    categoryRespo.save(category);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRespo.deleteById(id);
  }
}
