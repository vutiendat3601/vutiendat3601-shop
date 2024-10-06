package vn.io.vutiendat3601.shop.v2.product;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
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

@RequiredArgsConstructor
@RequestMapping("v2/products")
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<PageDto<ProductDto>> getProductsByCategoryCode(
      @RequestParam String categoryCode,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<ProductDto> productDtoPage =
        productService.getProductsByCategoryCode(categoryCode, page, size);
    return ResponseEntity.ok(productDtoPage);
  }

  @GetMapping("trending")
  public ResponseEntity<PageDto<ProductDto>> getTrendingProducts(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<ProductDto> productDtoPage = productService.getTrendingProducts(page, size);
    return ResponseEntity.ok(productDtoPage);
  }

  @PostMapping
  public ResponseEntity<?> createProduct(
      @Validated @RequestBody CreateProductRequest createProductReq) {
    productService.createProduct(createProductReq);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{product_no}")
  public ResponseEntity<?> updateProduct(
      @PathVariable("product_no") String productNo,
      @RequestBody UpdateProductRequest updateProductRequest) {
    productService.updateProduct(productNo, updateProductRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{product_no}")
  public ResponseEntity<?> deleteProduct(@PathVariable("product_no") String productNo) {
    productService.deleteProduct(productNo);
    return ResponseEntity.ok().build();
  }

  @PostMapping("unitprice/{product_no}")
  public ResponseEntity<PriceHistoryDto> updateUnitPrice(
      @PathVariable("product_no") String productNo, @RequestBody @NonNull BigDecimal newUnitPrice) {
    productService.updateUnitPrice(productNo, newUnitPrice);

    return ResponseEntity.ok().build();
  }
}
