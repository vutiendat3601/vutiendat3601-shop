package vn.io.vutiendat3601.shop.v2.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryJpaDataAccesService implements CategoryDao {

  final CategoryRespository categoryRespo;
  @Override
  public List<Category> selectAllCategories() {
    return categoryRespo.findAll();
  }
  @Override
  public Optional<Category> selectById(long id) {
    return categoryRespo.findById(id);
  }


}
