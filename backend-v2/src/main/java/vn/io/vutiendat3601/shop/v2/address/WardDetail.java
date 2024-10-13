package vn.io.vutiendat3601.shop.v2.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "v_ward_detail", schema = "core")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WardDetail extends AuditEntity {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "province_id")
  private Long provinceId;

  @Column(name = "province_name")
  private String provinceName;

  @Column(name = "district_id")
  private Long districtId;

  @Column(name = "district_name")
  private String districtName;

  @Column(name = "name")
  private String name;
}
