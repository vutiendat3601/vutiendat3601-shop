package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
  List<PriceHistory> findByProductId(long productId, Pageable pageable);
}
