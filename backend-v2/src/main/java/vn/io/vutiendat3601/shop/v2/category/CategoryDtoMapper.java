package vn.io.vutiendat3601.shop.v2.category;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.lang.Assert;

@Component
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

  @Override
  public CategoryDto apply(Category category) {
    Assert.notNull(category, "category argument must not be null");
    return new CategoryDto(category.getId(), category.getName());
  }
 
}
