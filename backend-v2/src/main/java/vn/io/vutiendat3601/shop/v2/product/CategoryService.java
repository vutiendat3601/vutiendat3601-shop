package vn.io.vutiendat3601.shop.v2.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.exception.RequestValidationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceDuplicationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

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

  @Transactional
  public void createCategory(CreateCategoryRequest createCategoryReq) {
    if (categoryDao.existsCategoryByCode(createCategoryReq.code())) {
      throw new ResourceDuplicationException(
          "Product slug already taken".formatted(createCategoryReq.code()));
    }
    if (createCategoryReq.code() == null || createCategoryReq.code().isEmpty()) {
      throw new IllegalArgumentException("Category code cannot be null or empty");
    }
    if (createCategoryReq.slug() == null || createCategoryReq.slug().isEmpty()) {
      throw new IllegalArgumentException("Category slug cannot be null or empty");
    }
    final Category category =
        Category.builder()
            .code(createCategoryReq.code())
            .slug(createCategoryReq.slug())
            .name(createCategoryReq.name())
            .thumbnail(createCategoryReq.thumbnail())
            .build();
    categoryDao.insertCategory(category);
  }

  public Category getCategory(Long id) {
    return categoryDao
        .selectById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Category with id [%s] not found".formatted(id)));
  }

  @Transactional
  public void updateCategory(Long id, UpdateCategoryRequest updateCategoryReq) {
    Category category = getCategory(id);
    boolean isChange = false;

    if (updateCategoryReq.code() != null && !updateCategoryReq.code().equals(category.getCode())) {
      category.setCode(updateCategoryReq.code());
      isChange = true;
    }
    if (updateCategoryReq.slug() != null && !updateCategoryReq.slug().equals(category.getSlug())) {
      category.setSlug(updateCategoryReq.slug());
      isChange = true;
    }
    if (updateCategoryReq.name() != null && !updateCategoryReq.name().equals(category.getName())) {
      category.setName(updateCategoryReq.name());
      isChange = true;
    }

    if (updateCategoryReq.thumbnail() != null
        && !updateCategoryReq.thumbnail().equals(category.getThumbnail())) {
      category.setThumbnail(updateCategoryReq.thumbnail());
      isChange = true;
    }

    if (!isChange) {
      throw new RequestValidationException("No data changes found");
    }

    categoryDao.updateCategory(category);
  }

  @Transactional
  public void deleteProduct(Long id) {
    getCategory(id);
    categoryDao.deleteCategory(id);
  }
}
