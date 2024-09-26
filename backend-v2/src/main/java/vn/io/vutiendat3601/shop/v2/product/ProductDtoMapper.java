package vn.io.vutiendat3601.shop.v2.product;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProductDtoMapper implements Function<Product, ProductDto> {

  @Override
  public ProductDto apply(Product product) {
    Assert.notNull(product, "product argument must not be null");
    return new ProductDto(
        product.getId(),
        product.getSku(),
        product.getName(),
        product.getDescription(),
        product.getUnitPrice(),
        product.getUnitListedPrice(),
        product.getImage(),
        product.getBuyedCount(),
        product.getTags(),
        product.getLikedCount(),
        product.getIsActive(),
        product.getUnitsInStock()
       );
  }
}
