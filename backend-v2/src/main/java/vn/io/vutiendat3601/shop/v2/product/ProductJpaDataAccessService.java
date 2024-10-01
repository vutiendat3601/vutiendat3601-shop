package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
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

  public Page<Product> selectByCategoryCodeAndIsActiveTrue(
      @NonNull String categoryCode, int page, int size) {
    return productRepo.findAllByCategoryCodeAndIsActiveTrue(
        categoryCode, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Optional<Product> selectById(Long productId) {
    return productRepo.findById(productId);
  }

  @NonNull
  public Optional<Product> selectByProductNoAndIsActiveTrue(@NonNull String productNo) {
    return productRepo.findByProductNoAndIsActiveTrue(productNo);
  }
}
