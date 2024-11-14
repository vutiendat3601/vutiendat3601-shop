package vn.io.vutiendat3601.shop.v2.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

class ProductDetailJpaDataAccessServiceTest {

    @InjectMocks
    private ProductDetailJpaDataAccessService productDetailJpaDataAccessService;

    @Mock
    private ProductDetailRepository productDetailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProductDetailWhenProductNoExists() {
        // Given
        String productNo = "P001";
        Product mockProduct = mock(Product.class);
        when(productDetailRepository.findByProductNo(productNo)).thenReturn(Optional.of(mockProduct));

        // When
        Optional<Product> result = productDetailJpaDataAccessService.selectProducDetail(productNo);

        // Then
        assertTrue(result.isPresent(), "Result should be present when product exists");
        assertEquals(mockProduct, result.get(), "Result should match the expected product");
        verify(productDetailRepository, times(1)).findByProductNo(productNo);
    }

    @Test
    void shouldReturnEmptyWhenProductNoDoesNotExist() {
        // Given
        String productNo = "P002";
        when(productDetailRepository.findByProductNo(productNo)).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productDetailJpaDataAccessService.selectProducDetail(productNo);

        // Then
        assertFalse(result.isPresent(), "Result should not be present when product does not exist");
        verify(productDetailRepository, times(1)).findByProductNo(productNo);
    }

    @Test
    void shouldReturnPaginatedProductsWhenPageAndSizeProvided() {
        // Given
        int page = 0;
        int size = 10;
        Product mockProduct = mock(Product.class);
        List<Product> products = Collections.singletonList(mockProduct);
        Page<Product> mockPage = new PageImpl<>(products, PageRequest.of(page, size), products.size());
        when(productDetailRepository.findAll(PageRequest.of(page, size))).thenReturn(mockPage);

        // When
        Page<Product> result = productDetailJpaDataAccessService.selectAll(page, size);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.getContent().size(), "Result size should match the mock data");
        assertEquals(mockProduct, result.getContent().get(0), "Content should match the expected product");
        verify(productDetailRepository, times(1)).findAll(PageRequest.of(page, size));
    }
}
