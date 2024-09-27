package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductDao productDao;
  private final ProductDtoMapper productDtoMapper;

  @NonNull
  public List<ProductDto> getProductsByCategoryId(long categoryId) {
    final List<Product> products = productDao.selectByCategoryId(categoryId);
    return products.stream().map(productDtoMapper).toList();
  }
}
