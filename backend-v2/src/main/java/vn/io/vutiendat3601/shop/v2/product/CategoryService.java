package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryDao categoryDao;
  private final CategoryDtoMapper categoryDtoMapper;

  public List<CategoryDto> getCategories() {
    final List<Category> categories = categoryDao.selectAll();
    return categories.stream().map(categoryDtoMapper).toList();
  }
}
