package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderJpaDataAccessService implements OrderDao {
  private final OrderRepository orderRepo;

  @Override
  public long insert(@NonNull Order order) {
    return orderRepo.save(order).getId();
  }

  @Override
  @NonNull
  public Optional<Order> selectByTrackingNumber(@NonNull String trackingNumber) {
    return orderRepo.findByTrackingNumber(trackingNumber);
  }

  @Override
  @NonNull
  public Optional<Order> selectById(long id) {
    return orderRepo.findById(id);
  }
}
