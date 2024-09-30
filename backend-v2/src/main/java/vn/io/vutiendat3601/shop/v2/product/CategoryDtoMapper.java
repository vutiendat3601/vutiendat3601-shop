package vn.io.vutiendat3601.shop.v2.product;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

  @Override
  public CategoryDto apply(Category category) {
    Assert.notNull(category, "category argument must not be null");
    return new CategoryDto(
        category.getId(),
        category.getCode(),
        category.getSlug(),
        category.getName(),
        category.getThumbnail());
  }
}
