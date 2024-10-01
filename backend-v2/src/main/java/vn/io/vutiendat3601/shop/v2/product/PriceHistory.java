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
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "price_history", schema = "business")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistory extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Builder.Default
  @Column(name = "price")
  private BigDecimal price = new BigDecimal(0L);

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
