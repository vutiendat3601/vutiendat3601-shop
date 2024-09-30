package vn.io.vutiendat3601.shop.v2.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryDao categoryDao;
  private final CategoryDtoMapper categoryDtoMapper;

  public PageDto<CategoryDto> getCategories(int page, int size) {
    page--;
    final Page<Category> categoryPage = categoryDao.selectAll(page, size);
    return PageDto.of(categoryPage).map(categoryDtoMapper);
  }
}
