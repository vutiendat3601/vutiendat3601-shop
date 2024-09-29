package vn.io.vutiendat3601.shop.v2.product;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class ProductDetaiDtoMapper implements Function<Product, ProductDetailDto> {

  @Override
  public ProductDetailDto apply(Product productDetail) {
    Assert.notNull(productDetail, "product argument must not be null");
    return new ProductDetailDto(
        productDetail.getProductNo(),
        productDetail.getSku(),
        productDetail.getSlug(),
        productDetail.getName(),
        productDetail.getDescription(),
        productDetail.getUnitPrice(),
        productDetail.getUnitListedPrice(),
        productDetail.getThumbnail(),
        productDetail.getBuyedCount(),
        productDetail.getLikedCount(),
        productDetail.getIsActive(),
        productDetail.getUnitsInStock(),
        productDetail.getCategory());
  }
}
