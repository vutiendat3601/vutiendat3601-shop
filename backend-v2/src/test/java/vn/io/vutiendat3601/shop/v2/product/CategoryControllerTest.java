package vn.io.vutiendat3601.shop.v2.product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.coupon.CouponDto;
import vn.io.vutiendat3601.shop.v2.coupon.CouponObjectType;
import vn.io.vutiendat3601.shop.v2.coupon.CouponService;
import vn.io.vutiendat3601.shop.v2.coupon.CouponType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CouponService couponService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CreateCategoryRequest createCategoryRequest;
    private UpdateCategoryRequest updateCategoryRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();

        createCategoryRequest = new CreateCategoryRequest("code1", "slug1", "Category1", "thumbnail1.png");
        updateCategoryRequest = new UpdateCategoryRequest("code1", "slug1", "Category1 Updated", "thumbnail1_updated.png");
    }

    @Test
    void getCategories_shouldReturnCategories() throws Exception {
        // Given
        CategoryDto categoryDto = new CategoryDto( 1L,"code1", "slug1", "Category1", "thumbnail1.png");
        PageDto<CategoryDto> categoryPage = new PageDto<>(Collections.singletonList(categoryDto), 1, 10, 1, 1);
        when(categoryService.getCategories(1, 10)).thenReturn(categoryPage);

        // When & Then
        mockMvc.perform(get("/v2/categories?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalItems").value(1));
        verify(categoryService).getCategories(1, 10);
    }

    @Test
    void createCategory_shouldCreateCategory() throws Exception {
        // When & Then
        mockMvc.perform(post("/v2/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCategoryRequest)))
                .andExpect(status().isOk());
        verify(categoryService).createCategory(createCategoryRequest);
    }

    @Test
    void updateCategory_shouldUpdateCategory() throws Exception {
        // When & Then
        mockMvc.perform(put("/v2/categories/{code}", "code1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCategoryRequest)))
                .andExpect(status().isOk());
        verify(categoryService).updateCategory("code1", updateCategoryRequest);
    }

    @Test
    void deleteCategory_shouldDeleteCategory() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v2/categories/{code}", "code1"))
                .andExpect(status().isOk());
        verify(categoryService).deleteProduct("code1");
    }

    @Test
    void getAvailableCouponByCategoryCode_shouldReturnCoupons() throws Exception {
        // Given
        CouponDto couponDto = new CouponDto(
                "coupon1",
                "Discount",
                "Hello",
                10.0,
                new BigDecimal("100.00"),
                ZonedDateTime.now().plusDays(10),
                "categoryCode1",
                "productNo1",
                "customerCode1",
                CouponType.RATIO,
                CouponObjectType.PRODUCT
        );
        PageDto<CouponDto> couponPage = new PageDto<>(Collections.singletonList(couponDto), 1, 10, 1, 1);
        when(couponService.getAvailableCouponByCategoryCode("code1", 1, 100)).thenReturn(couponPage);

        // When & Then
        mockMvc.perform(get("/v2/categories/{code}/coupons?page=1&size=100", "code1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalItems").value(1));
        verify(couponService).getAvailableCouponByCategoryCode("code1", 1, 100);
    }
}
