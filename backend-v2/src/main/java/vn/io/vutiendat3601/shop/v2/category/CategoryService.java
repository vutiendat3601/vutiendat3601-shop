package vn.io.vutiendat3601.shop.v2.category;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryDao categoryDao;
  private final CategoryDtoMapper categoryDtoMapper;

  public List<CategoryDto> getCategories() {
    final List<Category> categories = categoryDao.selectAllCategories();
    return categories.stream().map(categoryDtoMapper).toList();
  }

  @NotNull
  public Optional<CategoryDto> getCategoryById(Long id) {
    return categoryDao.selectById(id).map(categoryDtoMapper);
  }
}
