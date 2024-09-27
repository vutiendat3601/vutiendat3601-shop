package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductJpaDataAccessService implements ProductDao {
  private final ProductRepository productRepo;

  @Override
  public Optional<Product> selectById(long id) {
    return productRepo.findById(id);
  }

  @Override
  public List<Product> selectByCategoryId(long categoryId) {
    return productRepo.findAllByCategoryId(categoryId);
  }

  @Override
  public List<Product> selectAllProduct() {
    return productRepo.findAll();
  }
}
