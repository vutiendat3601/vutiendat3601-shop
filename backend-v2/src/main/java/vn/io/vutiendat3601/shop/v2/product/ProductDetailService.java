package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RequiredArgsConstructor
@Service
public class ProductDetailService {
  private final ProductDetailDao productDetailDao;
  private final ProductDetaiDtoMapper productDetaiDtoMapper;

  public Optional<ProductDetailDto> getDetailProductByProductNo(String productNo) {
    return productDetailDao.selectProducDetail(productNo).map(productDetaiDtoMapper);
  }

  public PageDto<ProductDetailDto> getAll(int page, int size) {
    page--;
    final Page<Product> prdPage = productDetailDao.selectAll(page, size);
    return PageDto.of(prdPage).map(productDetaiDtoMapper);
  }
}
