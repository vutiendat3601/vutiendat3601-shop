package vn.io.vutiendat3601.shop.v2.product;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import vn.io.vutiendat3601.shop.v2.coupon.CouponDto;
import vn.io.vutiendat3601.shop.v2.coupon.CouponService;

@Tag(name = "Product")
@RequiredArgsConstructor
@RequestMapping("v2/products")
@RestController
public class ProductController {
  private final ProductService productService;
  private final CouponService couponService;

  @GetMapping("search")
  public ResponseEntity<PageDto<ProductDto>> search(
      @RequestParam("keyword") String keyword,
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size) {
    final PageDto<ProductDto> productDtoPage = productService.search(keyword, page, size);
    return ResponseEntity.ok(productDtoPage);
  }

  @GetMapping("{productNo}/coupons")
  public ResponseEntity<PageDto<CouponDto>> getAvailableCouponByCategoryCode(
      @PathVariable(name = "productNo") String productNo,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "100") Integer size) {
    final PageDto<CouponDto> couponDtoPage =
        couponService.getAvailableCouponByProductNo(productNo, page, size);
    return ResponseEntity.ok(couponDtoPage);
  }

  @GetMapping
  public ResponseEntity<PageDto<ProductDto>> getProductsByCategoryCode(
      @RequestParam("categoryCode") String categoryCode,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
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

  @PutMapping("{productNo}")
  public ResponseEntity<?> updateProduct(
      @PathVariable("productNo") String productNo,
      @RequestBody UpdateProductRequest updateProductRequest) {
    productService.updateProduct(productNo, updateProductRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{productNo}")
  public ResponseEntity<?> deleteProduct(@PathVariable("productNo") String productNo) {
    productService.deleteProduct(productNo);
    return ResponseEntity.ok().build();
  }

  @PostMapping("unitprice/{productNo}")
  public ResponseEntity<PriceHistoryDto> updateUnitPrice(
      @PathVariable("productNo") String productNo, @RequestBody @NonNull BigDecimal newUnitPrice) {
    productService.updateUnitPrice(productNo, newUnitPrice);

    return ResponseEntity.ok().build();
  }
}
