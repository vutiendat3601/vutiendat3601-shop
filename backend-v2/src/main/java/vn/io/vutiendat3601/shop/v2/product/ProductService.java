package vn.io.vutiendat3601.shop.v2.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductDao productDao;
  private final ProductDtoMapper productDtoMapper;

  @NonNull
  public PageDto<ProductDto> getProductsByCategoryCode(
      @NonNull String categoryCode, int page, int size) {
    page--;
    final Page<Product> productPage =
        productDao.selectByCategoryCodeAndIsActiveTrue(categoryCode, page, size);
    return PageDto.of(productPage).map(productDtoMapper);
  }
}
