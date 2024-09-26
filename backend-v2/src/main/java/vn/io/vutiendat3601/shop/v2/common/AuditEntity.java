package vn.io.vutiendat3601.shop.v2.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class AuditEntity {
  @Column(name = "created_at")
  @CreatedDate
  protected ZonedDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  protected ZonedDateTime updatedAt;

  @Column(name = "created_by")
  @CreatedBy
  protected Long createdBy;

  @Column(name = "updated_by")
  @LastModifiedBy
  protected Long updatedBy;
}
