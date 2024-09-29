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
  public List<Product> selectByCategoryId(long categoryId) {
    return productRepo.findAllByCategoryId(categoryId);
  }

  @Override
  public void insertProduct(Product product) {
    productRepo.save(product);
  }

  @Override
  public void updateProduct(Product product) {
    productRepo.save(product);
  }

  @Override
  public void deleteProduct(String productNo) {
    productRepo.deleteByProductNo(productNo);
  }

  @Override
  public boolean existsProductByProductNo(String productNo) {
    return productRepo.existsProductByProductNo(productNo);
  }

  @Override
  public boolean existsProductBySku(String sku) {
    return productRepo.existsProductBySku(sku);
  }

  @Override
  public boolean existsProductBySlug(String slug) {
    return productRepo.existsProductBySlug(slug);
  }

  @Override
  @NonNull
  public Optional<Product> selectByProductNo(String productNo) {
    return productRepo.findByProductNo(productNo);
  }

  @Override
  @NonNull
  public Optional<Product> selectById(Long productId) {
    return productRepo.findById(productId);
  }
}
