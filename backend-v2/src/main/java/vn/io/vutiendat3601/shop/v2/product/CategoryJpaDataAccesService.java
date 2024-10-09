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
  public void insert(Category category) {
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
  @NonNull
  public Optional<Category> selectByCode(String code) {
   return categoryRespo.findByCode(code);
  }

  @Override
  public void deleteCategory(String code) {
   categoryRespo.deleteByCode(code);
  }

  @Override
  @NonNull
  public Optional<Category> selectById(Long id) {
    return categoryRespo.findById(id);
  }
}
