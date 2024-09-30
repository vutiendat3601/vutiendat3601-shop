package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @NonNull
  Optional<Product> findByProductNoAndIsActiveTrue(@NonNull String productNo);

  @NonNull
  Page<Product> findAllByCategoryCodeAndIsActiveTrue(
      @NonNull String categoryCode, @NonNull Pageable pageable);
}
