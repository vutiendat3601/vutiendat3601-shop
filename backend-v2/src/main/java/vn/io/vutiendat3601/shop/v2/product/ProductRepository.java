package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @NonNull
  Optional<Product> findByProductNo(String productNo);

  void deleteByProductNo(String productNo);

  @NonNull
  List<Product> findAllByCategoryId(long categoryId);

  boolean existsProductByProductNo(String productNo);

  boolean existsProductBySku(String sku);

  boolean existsProductBySlug(String slug);

  @NonNull
  Optional<Product> findByProductNoAndIsActiveTrue(@NonNull String productNo);

  @Query(
      "SELECT p FROM Product p JOIN Category c ON p.category.id = c.id WHERE c.code ="
          + " :categoryCode")
  Page<Product> findAllByCategoryCode(
      @Param("categoryCode") String categoryCode, Pageable pageable);

  Optional<Product> findUnitInStocksById(Long id);

  @NonNull
  Page<Product> findByOrderByBuyedCountDesc(@NonNull Pageable pageable);

  @NonNull
  Page<Product> findByNameContaining(@NonNull String keyword, @NonNull Pageable pageable);
}
