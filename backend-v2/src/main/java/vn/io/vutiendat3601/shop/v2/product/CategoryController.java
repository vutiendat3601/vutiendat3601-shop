package vn.io.vutiendat3601.shop.v2.product;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RestController
@RequestMapping("v2/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<PageDto<CategoryDto>> getCategories(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<CategoryDto> categoryDtoPage = categoryService.getCategories(page, size);
    return ResponseEntity.ok(categoryDtoPage);
  }

  @PostMapping
  public ResponseEntity<?> createCategory(@Parameter CreateCategoryRequest createCategoryRequest) {
    categoryService.createCategory(createCategoryRequest);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<?> updateCategory(
      @PathVariable("id") Long id, @Parameter UpdateCategoryRequest updateCategoryRequest) {
    categoryService.updateCategory(id, updateCategoryRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
    categoryService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }
}
