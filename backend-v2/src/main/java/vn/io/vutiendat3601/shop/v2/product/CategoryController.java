package vn.io.vutiendat3601.shop.v2.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
