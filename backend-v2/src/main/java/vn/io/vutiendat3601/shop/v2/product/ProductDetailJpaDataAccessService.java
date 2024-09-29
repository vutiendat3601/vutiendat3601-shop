package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductDetailJpaDataAccessService implements ProductDetailDao {
  private final ProductDetailRepository proDetailRepo;

  @Override
  public List<Product> selectAll() {
    return proDetailRepo.findAll();
  }

  @Override
  public Optional<Product> selectProducDetail(String productNo) {
    return proDetailRepo.findByProductNo(productNo);
  }
}
