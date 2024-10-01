package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RestController
@RequestMapping("v2/product-all")
@RequiredArgsConstructor
public class ProductDetailController {
  private final ProductDetailService productDetailService;

  @GetMapping()
  public ResponseEntity<?> getProduct(
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    final PageDto<ProductDetailDto> prPageDto = productDetailService.getAll(page, size);
    return ResponseEntity.ok(prPageDto);
  }

  @GetMapping("{product_no}/detail")
  public ResponseEntity<?> getProductDetails(@PathVariable("product_no") String productNo) {
    final Optional<ProductDetailDto> prOptional =
        productDetailService.getDetailProductByProductNo(productNo);
    return ResponseEntity.ok(prOptional);
  }
}
