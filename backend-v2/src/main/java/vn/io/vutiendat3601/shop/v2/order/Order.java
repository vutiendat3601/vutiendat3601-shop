package vn.io.vutiendat3601.shop.v2.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.address.Address;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.coupon.Coupon;
import vn.io.vutiendat3601.shop.v2.customer.Customer;

@Entity
@Table(name = "orders", schema = "bussiness")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "tracking_number")
  private String trackingNumber;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Builder.Default
  @Column(name = "num_of_products")
  private Integer numberOfProducts = 0;

  @Builder.Default
  @Column(name = "total_product_amount")
  private BigDecimal totalProductAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "total_product_coupon_amount")
  private BigDecimal totalProductCouponAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "total_product_final_amount")
  private BigDecimal totalProductFinalAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "vat_fee_amount")
  private BigDecimal vatFeeAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "shipping_fee_amount")
  private BigDecimal shippingFeeAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "shipping_fee_coupon_amount")
  private BigDecimal shippingFeeCouponAmount = new BigDecimal(0D);

  @Builder.Default
  @Column(name = "final_amount")
  private BigDecimal finalAmount = new BigDecimal(0D);

  @ManyToOne
  @JoinColumn(name = "shipping_fee_coupon_id")
  private Coupon shipingFeeCoupon;

  @ManyToOne
  @JoinColumn(name = "shipping_address_id")
  private Address shippingAddress;

  @JoinColumn(name = "customer_id")
  @ManyToOne
  private Customer customer;

  @Builder.Default
  @OneToMany(mappedBy = "order")
  private List<OrderItem> items = new ArrayList<>();

  public void addItem(@NonNull OrderItem item) {
    if (Objects.isNull(item.getId())) {
      items.add(item);
      numberOfProducts = items.size();
      totalProductAmount = totalProductAmount.add(item.getTotalAmount());
      totalProductCouponAmount = totalProductCouponAmount.add(item.getCouponAmount());
      totalProductFinalAmount = totalProductFinalAmount.add(item.getFinalAmount());
    }
  }
}
