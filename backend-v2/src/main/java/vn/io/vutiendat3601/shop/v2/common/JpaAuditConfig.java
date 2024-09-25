package vn.io.vutiendat3601.shop.v2.common;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class JpaAuditConfig {
  @Bean
  AuditorAware<Long> auditorAware() {
    return () -> Optional.ofNullable(1L);
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now());
  }
}
