package vn.io.vutiendat3601.shop.v2.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryJpaDataAccesServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryJpaDataAccesService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryJpaDataAccesService(categoryRepository);
    }

    @Test
    void selectAll() {
        // Given
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        when(categoryRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Category())));

        // When
        Page<Category> categories = categoryService.selectAll(page, size);

        // Then
        assertEquals(1, categories.getTotalElements());
        verify(categoryRepository).findAll(pageRequest);
    }

    @Test
    void insert() {
        // Given
        Category category = new Category(null, "code1", "slug1", "name1", "thumbnail1");

        // When
        categoryService.insert(category);

        // Then
        verify(categoryRepository).save(category);
    }

    @Test
    void existsCategoryByCode() {
        // Given
        final String code = "code1";
        when(categoryRepository.existsCategoryByCode(code)).thenReturn(true);

        // When
        boolean exists = categoryService.existsCategoryByCode(code);

        // Then
        assertTrue(exists);
        verify(categoryRepository).existsCategoryByCode(code);
    }

    @Test
    void updateCategory() {
        // Given
        Category category = new Category(null, "code1", "slug1", "name1", "thumbnail1");

        // When
        categoryService.updateCategory(category);

        // Then
        verify(categoryRepository).save(category);
    }

    @Test
    void selectByCode() {
        // Given
        final String code = "code1";
        Category category = new Category(null, code, "slug1", "name1", "thumbnail1");
        when(categoryRepository.findByCode(code)).thenReturn(Optional.of(category));

        // When
        Optional<Category> retrievedCategory = categoryService.selectByCode(code);

        // Then
        assertTrue(retrievedCategory.isPresent());
        assertEquals("slug1", retrievedCategory.get().getSlug());
        verify(categoryRepository).findByCode(code);
    }

    @Test
    void deleteCategory() {
        // Given
        final String code = "code1";

        // When
        categoryService.deleteCategory(code);

        // Then
        verify(categoryRepository).deleteByCode(code);
    }

    @Test
    void selectById() {
        // Given
        final Long id = 1L;
        Category category = new Category(id, "code1", "slug1", "name1", "thumbnail1");
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        // When
        Optional<Category> retrievedCategory = categoryService.selectById(id);

        // Then
        assertTrue(retrievedCategory.isPresent());
        assertEquals("code1", retrievedCategory.get().getCode());
        verify(categoryRepository).findById(id);
    }
}
