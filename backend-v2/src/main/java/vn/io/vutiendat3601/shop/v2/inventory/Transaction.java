package vn.io.vutiendat3601.shop.v2.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "transaction", schema = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TransactionType transactionType; 

  @Column(name = "before_quantity")
  private Long beforeQuantity;


  @Column(name = "quantity_to_stocks")
  private Long quantityToStocks;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "description")
  private String description;

  @Column(name = "receipt_id")
  private Long receiptId;
}
