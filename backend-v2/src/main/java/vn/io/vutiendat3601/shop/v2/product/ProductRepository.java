package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @NonNull
  List<Product> findAllByCategoryId(long categoryId);

  @NonNull
  Optional<Product> findByProductNoAndIsActiveTrue(@NonNull String productNo);
}
