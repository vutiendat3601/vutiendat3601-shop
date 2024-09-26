package vn.io.vutiendat3601.shop.v2.category;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("v2/categories")
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    final List<CategoryDto> categoryDtos = categoryService.getCategories();
    return ResponseEntity.ok(categoryDtos);
  }

  @GetMapping("v2/categories/{id}")
  public ResponseEntity<Optional<CategoryDto>> getCategoryById(@PathVariable Long id){
    final Optional<CategoryDto> categoryDto = categoryService.getCategoryById(id);
    return ResponseEntity.ok(categoryDto);
  }
}
