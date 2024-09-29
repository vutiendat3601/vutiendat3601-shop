package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import lombok.Generated;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class PriceHistoryJpaDataAccessService implements PriceHistoryDao {
  private final PriceHistoryRepository priceHistoryRepo;

  public void insert(@NonNull PriceHistory priceHistory) {
    priceHistoryRepo.save(priceHistory);
  }

  @Generated
  public PriceHistoryJpaDataAccessService(final PriceHistoryRepository priceHistoryRepo) {
    this.priceHistoryRepo = priceHistoryRepo;
  }

  @Override
  @NonNull
  public List<PriceHistory> selectByProductId(long productId, int page, int size) {
    return priceHistoryRepo.findByProductId(productId, PageRequest.of(page, size));
  }
}
