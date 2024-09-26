package vn.io.vutiendat3601.shop.v2.user;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;

@Entity
@Table(name = "users", schema = "core")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "hashed_password")
  private String hashedPassword;

  @Builder.Default
  @Column(name = "is_verified")
  private Boolean isVerified = false;

  @Builder.Default
  @Column(name = "authorities")
  @Type(value = ListArrayType.class)
  private List<String> authorities = new ArrayList<>();

  public User(Long id) {
    this.id = id;
  }
}
