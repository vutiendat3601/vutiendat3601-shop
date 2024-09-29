package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/products")
@RequiredArgsConstructor
public class ProductDetailController {
  private final ProductDetailService productDetailService;

  @GetMapping()
  public ResponseEntity<?> getProduct() {
    final List<ProductDetailDto> productDetaiDto = productDetailService.getAll();
    return ResponseEntity.ok(productDetaiDto);
  }

  @GetMapping("{product_no}/detail")
  public ResponseEntity<?> getProductDetails(@PathVariable("product_no") String productNo) {
    final Optional<ProductDetailDto> prOptional =
        productDetailService.getDetailProductByProductNo(productNo);
    return ResponseEntity.ok(prOptional);
  }
}
