package vn.io.vutiendat3601.shop.v2.customer;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.io.vutiendat3601.shop.v2.common.AuditEntity;
import vn.io.vutiendat3601.shop.v2.user.User;

@Entity
@Table(name = "customer", schema = "core")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder.Default
  @Type(ListArrayType.class)
  @Column(name = "phones")
  private List<String> phones = new ArrayList<>();

  public Customer(Long id) {
    this.id = id;
  }
}
