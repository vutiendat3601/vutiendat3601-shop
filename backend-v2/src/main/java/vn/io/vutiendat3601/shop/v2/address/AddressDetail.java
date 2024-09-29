package vn.io.vutiendat3601.shop.v2.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "v_address_detail", schema = "core")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Immutable
@AllArgsConstructor
@Builder
public class AddressDetail extends AuditEntity {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "province_id")
  private Long provinceId;

  @Column(name = "province_name")
  private String provinceName;

  @Column(name = "district_id")
  private Long districtId;

  @Column(name = "district_name")
  private String districtName;

  @Column(name = "street")
  private String street;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "customer_code")
  private String customerCode;
}
