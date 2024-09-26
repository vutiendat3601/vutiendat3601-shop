package vn.io.vutiendat3601.shop.v2.verification;

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
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.user.User;

@Entity
@Table(name = "verification", schema = "common")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Verification extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "expired_at")
  private ZonedDateTime expiredAt;

  @Builder.Default
  @Column(name = "is_disabled")
  private Boolean isDisabled = false;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private VerificationType type;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
