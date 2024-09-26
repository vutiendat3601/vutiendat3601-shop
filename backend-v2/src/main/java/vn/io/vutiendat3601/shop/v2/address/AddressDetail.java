package vn.io.vutiendat3601.shop.v2.address;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "v_address_detail")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Immutable
public class AddressDetail extends AuditEntity {
  @Id
  private Long id;
  @Column(name = "province")
  private String province;

  @Column(name = "district")
  private String district;

  @Column(name = "street")
  private String street;

  @Column(name = "customer_id")
  private Long customerId;
}
