package vn.io.vutiendat3601.shop.v2.product;

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
}
