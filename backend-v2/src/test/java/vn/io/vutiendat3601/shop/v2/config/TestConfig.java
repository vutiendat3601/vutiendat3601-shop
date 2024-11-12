package vn.io.vutiendat3601.shop.v2.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class TestConfig {
  @Bean
  AuditorAware<Long> auditorAware() {
    return () -> Optional.ofNullable(0L);
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now(ZoneOffset.UTC));
  }
}
