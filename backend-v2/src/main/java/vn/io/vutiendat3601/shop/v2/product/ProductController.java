package vn.io.vutiendat3601.shop.v2.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RequiredArgsConstructor
@RequestMapping("v2/products")
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping()
  public ResponseEntity<PageDto<ProductDto>> getProductsByCategoryCode(
      @RequestParam String categoryCode,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<ProductDto> productDtoPage =
        productService.getProductsByCategoryCode(categoryCode, page, size);
    return ResponseEntity.ok(productDtoPage);
  }
}
