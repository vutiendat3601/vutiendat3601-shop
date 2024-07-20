package vn.io.vutiendat3601.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sku;

  private String name;

  private String description;

  private BigDecimal unitPrice;

  private String imageUrl;

  private boolean active;

  private int unitsInStock;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private ProductCategory category;

  @CreationTimestamp private Date dateCreated;

  @UpdateTimestamp private Date lastUpdated;
}
