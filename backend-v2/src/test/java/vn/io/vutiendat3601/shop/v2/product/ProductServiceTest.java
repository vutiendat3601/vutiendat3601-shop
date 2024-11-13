package vn.io.vutiendat3601.shop.v2.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @Mock
    private PriceHistoryDao priceHistoryDao;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProductWhenProductExists() {
        // Given
        String productNo = "202311130001";
        Product product = Product.builder()
                .productNo(productNo)
                .name("Test Product")
                .sku("SKU123")
                .unitPrice(BigDecimal.valueOf(100))
                .build();

        when(productDao.selectByProductNo(productNo)).thenReturn(Optional.of(product));

        // When
        Product actualProduct = productService.getProduct(productNo);

        // Then
        assertNotNull(actualProduct);
        assertEquals(productNo, actualProduct.getProductNo());
        assertEquals("Test Product", actualProduct.getName());
        verify(productDao).selectByProductNo(productNo);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        String productNo = "202311130002";
        when(productDao.selectByProductNo(productNo)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProduct(productNo)
        );

        assertEquals("Product with product_no [202311130002] not found", exception.getMessage());
        verify(productDao).selectByProductNo(productNo);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        // Given
        CreateProductRequest request = new CreateProductRequest(
                "SKU001", "test-product-slug", "Test Product",
                "Product description", BigDecimal.valueOf(100), BigDecimal.valueOf(120),
                "thumbnail.jpg", 1L
        );

        Category category = Category.builder().id(1L).name("Test Category").build();

        when(categoryDao.selectById(request.categoryId())).thenReturn(Optional.of(category));
        when(productDao.existsProductBySku(request.sku())).thenReturn(false);
        when(productDao.existsProductBySlug(request.slug())).thenReturn(false);

        // When
        productService.createProduct(request);

        // Then
        verify(categoryDao).selectById(request.categoryId());
        verify(productDao).insertProduct(argThat(product ->
                product.getSku().equals(request.sku()) &&
                product.getName().equals(request.name()) &&
                product.getCategory().equals(category)
        ));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // Given
        CreateProductRequest request = new CreateProductRequest(
                "SKU001", "test-product-slug", "Test Product",
                "Product description", BigDecimal.valueOf(100), BigDecimal.valueOf(120),
                "thumbnail.jpg", 1L
        );

        when(categoryDao.selectById(request.categoryId())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.createProduct(request)
        );

        assertEquals("Category not found", exception.getMessage());
        verify(categoryDao).selectById(request.categoryId());
    }

    @Test
    void shouldUpdateUnitPriceWhenValid() {
        // Given
        String productNo = "202311130001";
        Product product = Product.builder()
                .productNo(productNo)
                .unitPrice(BigDecimal.valueOf(100))
                .unitListedPrice(BigDecimal.valueOf(120))
                .build();

        BigDecimal newUnitPrice = BigDecimal.valueOf(90);

        when(productDao.selectByProductNo(productNo)).thenReturn(Optional.of(product));

        // When
        productService.updateUnitPrice(productNo, newUnitPrice);

        // Then
        verify(priceHistoryDao).insert(argThat(priceHistory ->
                priceHistory.getPrice().equals(newUnitPrice)
        ));
        verify(productDao).updateProduct(argThat(updatedProduct ->
                updatedProduct.getUnitPrice().equals(newUnitPrice)
        ));
    }

    @Test
    void shouldThrowExceptionWhenNewUnitPriceIsNull() {
        // Given
        String productNo = "202311130001";
        when(productDao.selectByProductNo(productNo)).thenReturn(Optional.of(new Product()));

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.updateUnitPrice(productNo, null)
        );

        assertEquals("The 'new unit price' cannot be null", exception.getMessage());
    }

    @Test
    void shouldDeleteProductWhenProductExists() {
        // Given
        String productNo = "202311130001";
        when(productDao.existsProductByProductNo(productNo)).thenReturn(true);

        // When
        productService.deleteProduct(productNo);

        // Then
        verify(productDao).deleteProduct(productNo);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        // Given
        String productNo = "202311130002";
        when(productDao.existsProductByProductNo(productNo)).thenReturn(false);

        // When & Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProduct(productNo)
        );

        assertEquals("Product with id [202311130002] not found", exception.getMessage());
        verify(productDao).existsProductByProductNo(productNo);
    }
}
