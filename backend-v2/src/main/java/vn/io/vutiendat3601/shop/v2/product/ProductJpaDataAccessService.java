package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductJpaDataAccessService implements ProductDao {
  private final ProductRepository productRepo;

  @Override
  @NonNull
  public Optional<Product> selectById(long id) {
    return productRepo.findById(id);
  }

  @Override
  @NonNull
  public Page<Product> selectByCategoryCodeAndIsActiveTrue(
      @NonNull String categoryCode, int page, int size) {
    return productRepo.findAllByCategoryCodeAndIsActiveTrue(
        categoryCode, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Optional<Product> selectByProductNoAndIsActiveTrue(@NonNull String productNo) {
    return productRepo.findByProductNoAndIsActiveTrue(productNo);
  }
}
