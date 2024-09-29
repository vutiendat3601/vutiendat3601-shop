package vn.io.vutiendat3601.shop.v2.order;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderJpaDataAccessService implements OrderDao {
  private final OrderRepository orderRepo;

  @Override
  public void insert(@NonNull Order order) {
    orderRepo.save(order);
  }
}
