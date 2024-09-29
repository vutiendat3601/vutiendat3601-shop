package vn.io.vutiendat3601.shop.v2.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "config", schema = "business")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessConfiguration extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "key")
  private String key;

  @Column(name = "text_value")
  private String textValue;

  @Column(name = "boolean_value")
  private Boolean booleanValue;

  @Column(name = "number_value")
  private BigDecimal numberValue;

  @Column(name = "json_value")
  private String jsonValue;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "value_type")
  private BusinessConfigurationValueType valueType = BusinessConfigurationValueType.TEXT;
}
