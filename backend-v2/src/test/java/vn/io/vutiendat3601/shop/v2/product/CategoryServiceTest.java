package vn.io.vutiendat3601.shop.v2.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.exception.ResourceDuplicationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CategoryDtoMapper categoryDtoMapper;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCategories_shouldReturnPageDto() {
        // Given
        int page = 1;
        int size = 10;
        Category category = new Category();
        Page<Category> categoryPage = new PageImpl<>(List.of(category));
        when(categoryDao.selectAll(page - 1, size)).thenReturn(categoryPage);

        // When
        PageDto<CategoryDto> result = categoryService.getCategories(page, size);

        // Then
        assertNotNull(result);
        verify(categoryDtoMapper).apply(any(Category.class));
    }

    @Test
    void createCategory_shouldThrowResourceDuplicationException_whenCategoryExists() {
        // Given
        CreateCategoryRequest request = new CreateCategoryRequest("code1", "slug1", "name1", "thumbnail1");
        when(categoryDao.existsCategoryByCode(request.code())).thenReturn(true);

        // When & Then
        assertThrows(ResourceDuplicationException.class, () -> categoryService.createCategory(request));
        verify(categoryDao).existsCategoryByCode(request.code());
    }

    @Test
    void createCategory_shouldInsertCategory_whenValidRequest() {
        // Given
        CreateCategoryRequest request = new CreateCategoryRequest("code1", "slug1", "name1", "thumbnail1");
        when(categoryDao.existsCategoryByCode(request.code())).thenReturn(false);

        // When
        categoryService.createCategory(request);

        // Then
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryDao).insert(captor.capture());
        Category capturedCategory = captor.getValue();
        assertEquals(request.code(), capturedCategory.getCode());
        assertEquals(request.slug(), capturedCategory.getSlug());
        assertEquals(request.name(), capturedCategory.getName());
        assertEquals(request.thumbnail(), capturedCategory.getThumbnail());
    }

    @Test
    void getCategory_shouldReturnCategory_whenCategoryExists() {
        // Given
        String code = "code1";
        Category category = new Category();
        when(categoryDao.selectByCode(code)).thenReturn(Optional.of(category));

        // When
        Category result = categoryService.getCategory(code);

        // Then
        assertNotNull(result);
        verify(categoryDao).selectByCode(code);
    }

    @Test
    void getCategory_shouldThrowResourceNotFoundException_whenCategoryNotFound() {
        // Given
        String code = "code1";
        when(categoryDao.selectByCode(code)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(code));
    }

    @Test
    void updateCategory_shouldUpdateFields_whenValidChanges() {
        // Given
        String code = "code1";
        Category category = new Category();
        category.setCode(code);
        category.setSlug("old-slug");
        UpdateCategoryRequest request = new UpdateCategoryRequest("new-code", "new-slug", null, null);

        when(categoryDao.selectByCode(code)).thenReturn(Optional.of(category));

        // When
        categoryService.updateCategory(code, request);

        // Then
        verify(categoryDao).updateCategory(category);
        assertEquals("new-code", category.getCode());
        assertEquals("new-slug", category.getSlug());
    }

    @Test
    void deleteProduct_shouldCallDeleteCategory_whenCategoryExists() {
        // Given
        String code = "code1";
        Category category = new Category();
        when(categoryDao.selectByCode(code)).thenReturn(Optional.of(category));

        // When
        categoryService.deleteProduct(code);

        // Then
        verify(categoryDao).deleteCategory(code);
    }
}
