package vn.io.vutiendat3601.shop.v2.common;

import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class JpaAuditConfig {
  @Bean
  AuditorAware<Long> auditorAware(AuthContext authContext) {
    return () -> Optional.ofNullable(authContext.getUser().id());
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now());
  }
}
