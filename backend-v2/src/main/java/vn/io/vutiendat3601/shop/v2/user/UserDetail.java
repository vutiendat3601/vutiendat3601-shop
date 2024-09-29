package vn.io.vutiendat3601.shop.v2.user;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "v_user_detail", schema = "core")
@Immutable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail extends AuditEntity {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Builder.Default
  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  @Builder.Default
  @Column(name = "is_verified")
  private Boolean isVerified = false;

  @Column(name = "customer_code")
  private String customerCode;

  @Builder.Default
  @Type(value = ListArrayType.class)
  @Column(name = "authorities")
  private List<String> authorities = new ArrayList<>();
}
