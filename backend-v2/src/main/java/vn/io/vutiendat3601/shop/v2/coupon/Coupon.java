package vn.io.vutiendat3601.shop.v2.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.product.Category;
import vn.io.vutiendat3601.shop.v2.product.Product;

@Entity
@Table(name = "coupon", schema = "business")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @Column(name = "discount_ratio")
  private Double discountRatio = 0D;

  @Builder.Default
  @Column(name = "max_amount")
  private BigDecimal maxAmount = new BigDecimal(0D);

  @Column(name = "expired_at")
  private ZonedDateTime expiredAt;

  @Builder.Default
  @Column(name = "quantity")
  private Integer quantity = 0;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CouponType type;

  @Column(name = "object_type")
  @Enumerated(EnumType.STRING)
  private CouponObjectType objectType;
}
