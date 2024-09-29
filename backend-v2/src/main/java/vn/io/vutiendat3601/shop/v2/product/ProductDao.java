package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface ProductDao {
  @NonNull
  Optional<Product> selectByProductNo(String productNo);

  @NonNull
  Optional<Product> selectById(Long productId);

  @NonNull
  List<Product> selectByCategoryId(long categoryId);

  void insertProduct(Product product);

  void updateProduct(Product product);

  void deleteProduct(String productNo);

  boolean existsProductByProductNo(String ProductNo);

  boolean existsProductBySku(String sku);

  boolean existsProductBySlug(String slug);
}
