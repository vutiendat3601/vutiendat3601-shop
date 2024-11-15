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
import vn.io.vutiendat3601.shop.v2.common.PageDto;

import java.util.Collections;
import java.util.List;

class ProductDetailServiceTest {

    @InjectMocks
    private ProductDetailService productDetailService;

    @Mock
    private ProductDetailDao productDetailDao;

    @Mock
    private ProductDetaiDtoMapper productDetaiDtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProductDetailWhenProductNoExists() {
        // Given
        String productNo = "10003218378";
        Product mockProduct = mock(Product.class);
        ProductDetailDto mockDto = mock(ProductDetailDto.class);

        when(productDetailDao.selectProducDetail(productNo)).thenReturn(Optional.of(mockProduct));
        when(productDetaiDtoMapper.apply(mockProduct)).thenReturn(mockDto);

        // When
        Optional<ProductDetailDto> result = productDetailService.getDetailProductByProductNo(productNo);

        // Then
        assertTrue(result.isPresent(), "Product detail should be returned when the product exists");
        assertEquals(mockDto, result.get(), "Returned product detail should match expected");
        verify(productDetailDao).selectProducDetail(productNo);
        verify(productDetaiDtoMapper).apply(mockProduct);
    }

    @Test
    void shouldReturnEmptyWhenProductNoNotFound() {
        // Given
        String productNo = "P002";

        when(productDetailDao.selectProducDetail(productNo)).thenReturn(Optional.empty());

        // When
        Optional<ProductDetailDto> result = productDetailService.getDetailProductByProductNo(productNo);

        // Then
        assertFalse(result.isPresent(), "Product detail should not be returned when the product is not found");
        verify(productDetailDao).selectProducDetail(productNo);
        verifyNoInteractions(productDetaiDtoMapper);
    }

    @Test
    void shouldReturnPaginatedProductDetails() {
        // Given
        int page = 1;
        int size = 10;
        Product mockProduct = mock(Product.class);
        ProductDetailDto mockDto = mock(ProductDetailDto.class);
        List<Product> products = Collections.singletonList(mockProduct);
        Page<Product> mockPage = new PageImpl<>(products, PageRequest.of(page - 1, size), products.size());

        when(productDetailDao.selectAll(page - 1, size)).thenReturn(mockPage);
        when(productDetaiDtoMapper.apply(mockProduct)).thenReturn(mockDto);

        // When
        PageDto<ProductDetailDto> result = productDetailService.getAll(page, size);

        // Then
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.items(), "Items list should not be null");
        assertEquals(1, result.items().size(), "Items list should have exactly 1 element");
        assertEquals(mockDto, result.items().get(0), "The product detail DTO should match the expected DTO");
        verify(productDetailDao).selectAll(page - 1, size);
        verify(productDetaiDtoMapper).apply(mockProduct);
    }

    @Test
    void shouldReturnEmptyPageWhenNoProducts() {
        // Given
        int page = 1;
        int size = 10;
        List<Product> emptyProducts = Collections.emptyList();
        Page<Product> emptyPage = new PageImpl<>(emptyProducts, PageRequest.of(page - 1, size), 0);

        when(productDetailDao.selectAll(page - 1, size)).thenReturn(emptyPage);

        // When
        PageDto<ProductDetailDto> result = productDetailService.getAll(page, size);

        // Then
        assertNotNull(result, "Result should not be null");
        assertTrue(result.items().isEmpty(), "Items list should be empty when no products are available");
        assertEquals(0, result.items().size(), "Items list should have size 0 when there are no products");
        verify(productDetailDao).selectAll(page - 1, size);
        verifyNoInteractions(productDetaiDtoMapper); // No mapping needed if no products found
    }
}
