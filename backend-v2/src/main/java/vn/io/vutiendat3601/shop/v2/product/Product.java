package vn.io.vutiendat3601.shop.v2.product;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "product", schema = "business")
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

  @Column(name = "product_no")
  private String productNo;

  @Column(name = "sku")
  private String sku;

  @Column(name = "slug")
  private String slug;

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

  @Column(name = "thumbnail")
  private String thumbnail;

  @Builder.Default
  @Column(name = "buyed_count")
  private Long buyedCount = 0L;

  @Builder.Default
  @Type(value = ListArrayType.class)
  @Column(name = "tags")
  private List<String> tags = new ArrayList<>();

  @Builder.Default
  @Column(name = "liked_count")
  private Long likedCount = 0L;

  @Builder.Default
  @Column(name = "is_active")
  private Boolean isActive = false;

  @Builder.Default
  @Column(name = "units_in_stock")
  private Integer unitsInStock = 0;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "ref")
  private String ref;
}
