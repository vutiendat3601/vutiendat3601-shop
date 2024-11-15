package vn.io.vutiendat3601.shop.v2.product;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

class PriceHistoryJpaDataAccessServiceTest {

    @Mock
    private PriceHistoryRepository priceHistoryRepo;

    private PriceHistoryJpaDataAccessService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PriceHistoryJpaDataAccessService(priceHistoryRepo);
    }

    @Test
    void testInsert() {
        // Given
        PriceHistory priceHistory = new PriceHistory();

        // When
        service.insert(priceHistory);

        // Then
        verify(priceHistoryRepo, times(1)).save(priceHistory);
    }

    @Test
    void testSelectByProductId() {
        // Given
        long productId = 1L;
        int page = 0;
        int size = 10;
        List<PriceHistory> mockResults = Arrays.asList(new PriceHistory(), new PriceHistory());

        when(priceHistoryRepo.findByProductId(productId, PageRequest.of(page, size))).thenReturn(mockResults);

        // When
        List<PriceHistory> result = service.selectByProductId(productId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(priceHistoryRepo, times(1)).findByProductId(productId, PageRequest.of(page, size));
    }
}
