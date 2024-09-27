package vn.io.vutiendat3601.shop.crawler.product;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
  private Long id;

  private String productNo;

  private String sku;

  private String slug;

  private String name;

  private String description;

  @Builder.Default 
  private BigDecimal unitPrice = new BigDecimal(0D);

  @Builder.Default private BigDecimal unitListedPrice = new BigDecimal(0D);

  private String thumbnail;

  private Long buyedCount;

  private String[] tags;

  private Long likedCount;

  @Builder.Default private Boolean isActive = true;

  private Long unitsInStock;

  private String ref;
}
