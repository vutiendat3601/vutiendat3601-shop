package vn.io.vutiendat3601.shop.v2.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.category.Category;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "product", schema = "bussiness")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "sku")
  private String sku;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @Column(name = "unit_price")
  private BigDecimal unitPrice = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "unit_listed_price")
  private BigDecimal unitListedPrice = new BigDecimal(0D);

  @Column(name = "image")
  private String image;

  @Column(name = "buyed_count")
  private Long buyedCount;

  @Column(name = "tags")
  private String[] tags;

  @Column(name = "liked_count")
  private Long likedCount;

  @Builder.Default
  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "units_in_stock")
  private Long unitsInStock;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
