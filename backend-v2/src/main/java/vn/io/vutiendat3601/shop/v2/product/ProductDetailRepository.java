package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<Product, String> {
  Optional<Product> findByProductNo(String productNo);
}
