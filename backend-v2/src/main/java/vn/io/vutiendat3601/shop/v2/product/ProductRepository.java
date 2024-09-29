package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
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
}
