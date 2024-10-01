package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryJpaDataAccesService implements CategoryDao {
  private final CategoryRepository categoryRespo;

  @Override
  @NonNull
  public Page<Category> selectAll(int page, int size) {
    return categoryRespo.findAll(PageRequest.of(page, size));
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
