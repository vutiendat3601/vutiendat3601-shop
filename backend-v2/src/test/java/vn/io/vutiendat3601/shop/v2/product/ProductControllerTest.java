package vn.io.vutiendat3601.shop.v2.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.extension.ExtendWith;
import vn.io.vutiendat3601.shop.v2.common.PageDto;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductController productController;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(
                "P001", "sku1", "Product 1", "Description 1", new BigDecimal("100.00"),
                new BigDecimal("150.00"), "thumbnail_url", 10L, List.of("tag1", "tag2"), 5L, true, 20, "category1");
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnProductsWhenCategoryCodeIsProvided() {
        Page<ProductDto> productPage = new PageImpl<>(List.of(productDto), PageRequest.of(0, 10), 1);

        when(productService.getProductsByCategoryCode(anyString(), eq(1), eq(10)))
                .thenReturn(PageDto.of(productPage));

        ResponseEntity<PageDto<ProductDto>> response = productController.getProductsByCategoryCode("category1", 1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().totalPages());
        assertEquals(1, response.getBody().totalItems());
        assertEquals(1, response.getBody().items().size());
        assertEquals("Product 1", response.getBody().items().get(0).name());
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnTrendingProducts() {
        Page<ProductDto> productPage = new PageImpl<>(List.of(productDto), PageRequest.of(0, 10), 1);

        when(productService.getTrendingProducts(eq(1), eq(10)))
                .thenReturn(PageDto.of(productPage));

        ResponseEntity<PageDto<ProductDto>> response = productController.getTrendingProducts(1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().totalPages());
        assertEquals(1, response.getBody().totalItems());
        assertEquals(1, response.getBody().items().size());
        assertEquals("Product 1", response.getBody().items().get(0).name());
    }

    @SuppressWarnings("null")
    @Test
    void shouldSearchProductsByKeyword() {
        Page<ProductDto> productPage = new PageImpl<>(List.of(productDto), PageRequest.of(0, 10), 1);

        when(productService.search(anyString(), eq(1), eq(10)))
                .thenReturn(PageDto.of(productPage));

        ResponseEntity<PageDto<ProductDto>> response = productController.search("Product", 1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().totalPages());
        assertEquals(1, response.getBody().totalItems());
        assertEquals(1, response.getBody().items().size());
        assertEquals("Product 1", response.getBody().items().get(0).name());
    }

    @Test
    void shouldCreateProduct() {
        doNothing().when(productService).createProduct(any());

        CreateProductRequest createProductRequest = new CreateProductRequest(
            "sku1", "slug1", "Product 1", "Description 1", 
            new BigDecimal("100.00"), new BigDecimal("150.00"), 
            "thumbnail_url", 1L
        );

        ResponseEntity<?> response = productController.createProduct(createProductRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).createProduct(any());
    }

    @Test
    void shouldUpdateProduct() {
        doNothing().when(productService).updateProduct(anyString(), any());

        UpdateProductRequest updateProductRequest = new UpdateProductRequest(
                "sku1", "slug1", "Product 1", "Updated Description",
                new BigDecimal("100.00"), "updated_thumbnail_url", true, 1L);

        ResponseEntity<?> response = productController.updateProduct("P001", updateProductRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).updateProduct(eq("P001"), any());
    }

    @Test
    void shouldDeleteProduct() {
        doNothing().when(productService).deleteProduct(anyString());

        ResponseEntity<?> response = productController.deleteProduct("P001");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).deleteProduct(eq("P001"));
    }

}
