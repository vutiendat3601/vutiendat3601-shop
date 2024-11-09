package vn.io.vutiendat3601.shop.v2.testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestcontainersTest extends AbstractTestcontainersTest {
    @Test
    void canStartPostgreSQL() {
        assertTrue(POSTGRES_CONTAINER.isCreated());
        assertTrue(POSTGRES_CONTAINER.isRunning());
    }
}