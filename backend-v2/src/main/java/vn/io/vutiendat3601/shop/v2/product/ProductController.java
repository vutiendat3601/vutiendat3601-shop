package vn.io.vutiendat3601.shop.v2.product;

import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping("v2/categories/{categoryId}/products")
  public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Long categoryId) {
    final List<ProductDto> productDtos = productService.getProductsByCategoryId(categoryId);
    return ResponseEntity.ok(productDtos);
  }

  @PostMapping("v2/products")
  public ResponseEntity<?> createProduct(
      @Validated @RequestBody CreateProductRequest createProductReq) {
    productService.createProduct(createProductReq);
    return ResponseEntity.ok().build();
  }

  @PutMapping("v2/products/{product_no}")
  public ResponseEntity<?> updateProduct(
      @PathVariable("product_no") String productNo,
      @RequestBody UpdateProductRequest updateProductRequest) {
    productService.updateProduct(productNo, updateProductRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("v2/products/{product_no}")
  public ResponseEntity<?> deleteProduct(@PathVariable("product_no") String productNo) {
    productService.deleteProduct(productNo);
    return ResponseEntity.ok().build();
  }

  @PostMapping("v2/products/unitprice/{product_no}")
  public ResponseEntity<PriceHistoryDto> updateUnitPrice(
      @PathVariable("product_no") String productNo, @Parameter @NonNull BigDecimal newUnitPrice) {
    productService.updateUnitPrice(productNo, newUnitPrice);

    return ResponseEntity.ok().build();
  }
}
