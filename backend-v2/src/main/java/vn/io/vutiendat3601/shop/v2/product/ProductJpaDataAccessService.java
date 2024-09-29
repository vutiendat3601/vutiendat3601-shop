package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
  public List<Product> selectByCategoryId(long categoryId) {
    return productRepo.findAllByCategoryId(categoryId);
  }

  @Override
  @NonNull
  public Optional<Product> selectByProductNoAndIsActiveTrue(@NonNull String productNo) {
    return productRepo.findByProductNoAndIsActiveTrue(productNo);
  }
}
