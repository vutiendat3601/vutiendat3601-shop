package vn.io.vutiendat3601.shop.v2.product;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    final List<CategoryDto> categoryDtos = categoryService.getCategories();
    return ResponseEntity.ok(categoryDtos);
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
