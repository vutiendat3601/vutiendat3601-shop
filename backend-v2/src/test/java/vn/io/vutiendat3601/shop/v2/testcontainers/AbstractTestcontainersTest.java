package vn.io.vutiendat3601.shop.v2.testcontainers;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DirtiesContext
public abstract class AbstractTestcontainersTest {
  @Container protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

  static {
    POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:16-alpine3.20");
    POSTGRES_CONTAINER.withDatabaseName("shop");
    POSTGRES_CONTAINER.withUsername("shop");
    POSTGRES_CONTAINER.withPassword("123456Aa@");
  }

  @DynamicPropertySource
  static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    registry.add("spring.jpa.show-sql", () -> true);
  }

  @BeforeAll
  static void beforeAll() {
    final Flyway flyway =
        Flyway.configure()
            .dataSource(
                POSTGRES_CONTAINER.getJdbcUrl(),
                POSTGRES_CONTAINER.getUsername(),
                POSTGRES_CONTAINER.getPassword())
            .load();
    flyway.migrate();
  }
}
