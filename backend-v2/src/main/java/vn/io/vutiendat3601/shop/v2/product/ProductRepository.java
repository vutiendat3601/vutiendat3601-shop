package vn.io.vutiendat3601.shop.v2.product;

import io.micrometer.common.lang.NonNull;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE p.productNo = :productNo")
  Optional<Product> findByProductNo(String productNo);

  void deleteByProductNo(String productNo);

  List<Product> findAllByCategoryId(long categoryId);

  boolean existsProductByProductNo(String productNo);

  boolean existsProductBySku(String sku);

  boolean existsProductBySlug(String slug);

  @NonNull
  Optional<Product> findByProductNoAndIsActiveTrue(@NonNull String productNo);

  @NonNull
  Page<Product> findAllByCategoryCodeAndIsActiveTrue(
      @NonNull String categoryCode, @NonNull Pageable pageable);


  Optional<Product> findUnitInStocksById(Long id);
}
