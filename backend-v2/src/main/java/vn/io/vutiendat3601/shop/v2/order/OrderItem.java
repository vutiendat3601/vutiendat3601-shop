package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.coupon.Coupon;
import vn.io.vutiendat3601.shop.v2.product.Product;

@Entity
@Table(name = "order_item", schema = "business")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Builder.Default
  @Column(name = "quantity")
  private Integer quantity = 0;

  @Builder.Default
  @Column(name = "total_amount")
  private BigDecimal totalAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "coupon_amount")
  private BigDecimal couponAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "final_amount")
  private BigDecimal finalAmount = new BigDecimal(0D);

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "coupon_id")
  private Coupon coupon;
}
