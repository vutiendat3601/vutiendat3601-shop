package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByCategoryId(long categoryId);
}
