package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
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
}
