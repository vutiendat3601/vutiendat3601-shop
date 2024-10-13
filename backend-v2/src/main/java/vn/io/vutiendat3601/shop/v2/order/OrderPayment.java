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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.payment.PaymentMethod;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;

@Table(name = "order_payment", schema = "business")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderPayment extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "ref")
  private String ref;

  @Column(name = "amount")
  @Builder.Default
  private BigDecimal amount = new BigDecimal(0);

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  @Builder.Default
  private PaymentStatus status = PaymentStatus.PENDING;

  @Column(name = "message")
  private String message;

  @Column(name = "error_message")
  private String errorMessage;

  @Column(name = "payment_url")
  private String paymentUrl;

  @Column(name = "client_ip")
  private String clientIp;

  @Column(name = "method")
  @Enumerated(EnumType.STRING)
  private PaymentMethod method;

  @Column(name = "payment_url_expired_at")
  private ZonedDateTime paymentUrlExpiredAt;

  @Column(name = "callback_url")
  private String callbackUrl;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
