package vn.io.vutiendat3601.shop.v2.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.coupon.CouponDto;
import vn.io.vutiendat3601.shop.v2.coupon.CouponService;

@Tag(name = "Category")
@RestController
@RequestMapping("v2/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CouponService couponService;

  @GetMapping("{code}/coupons")
  public ResponseEntity<PageDto<CouponDto>> getAvailableCouponByCategoryCode(
      @PathVariable("code") String code,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "100") Integer size) {
    final PageDto<CouponDto> couponDtoPage =
        couponService.getAvailableCouponByCategoryCode(code, page, size);
    return ResponseEntity.ok(couponDtoPage);
  }

  @GetMapping
  public ResponseEntity<PageDto<CategoryDto>> getCategories(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<CategoryDto> categoryDtoPage = categoryService.getCategories(page, size);
    return ResponseEntity.ok(categoryDtoPage);
  }

  @PostMapping
  public ResponseEntity<?> createCategory(
      @RequestBody CreateCategoryRequest createCategoryRequest) {
    categoryService.createCategory(createCategoryRequest);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{code}")
  public ResponseEntity<?> updateCategory(
      @PathVariable("code") String code, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
    categoryService.updateCategory(code, updateCategoryRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{code}")
  public ResponseEntity<?> deleteCategory(@PathVariable("code") String code) {
    categoryService.deleteProduct(code);
    return ResponseEntity.ok().build();
  }
}
