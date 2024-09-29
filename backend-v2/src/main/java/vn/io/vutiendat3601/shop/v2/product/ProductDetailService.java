package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductDetailService {
  private final ProductDetailDao productDetailDao;
  private final ProductDetaiDtoMapper productDetaiDtoMapper;

  public Optional<ProductDetailDto> getDetailProductByProductNo(String productNo) {
    return productDetailDao.selectProducDetail(productNo).map(productDetaiDtoMapper);
  }

  public List<ProductDetailDto> getAll() {
    final List<Product> products = productDetailDao.selectAll();
    return products.stream().map(productDetaiDtoMapper).toList();
  }
}
