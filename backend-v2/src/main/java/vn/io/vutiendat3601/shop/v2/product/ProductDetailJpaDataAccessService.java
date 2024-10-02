package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductDetailJpaDataAccessService implements ProductDetailDao {
  private final ProductDetailRepository proDetailRepo;

  @Override
  public Optional<Product> selectProducDetail(String productNo) {
    return proDetailRepo.findByProductNo(productNo);
  }

  @Override
  public Page<Product> selectAll(int page, int size) {
    return proDetailRepo.findAll(PageRequest.of(page, size));
  }
}
