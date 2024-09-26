package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping("/v2/categories/{categoryId}/products")
  public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Long categoryId) {
    final List<ProductDto> productDtos = productService.getProductsByCategoryId(categoryId);
    return ResponseEntity.ok(productDtos);
  }

  @GetMapping("/v2/products")
  public ResponseEntity<List<ProductDto>>  getAllProducts(){
    final List<ProductDto> productDtos = productService.getAllProducts();
    return ResponseEntity.ok(productDtos);
  }

  @GetMapping("/v2/products/{id}")
  public ResponseEntity<Optional<ProductDto>> getProductById(@PathVariable("id") Long id){
    final Optional<ProductDto> prOptional = productService.getProductById(id);
    return ResponseEntity.ok(prOptional);
  }
}
