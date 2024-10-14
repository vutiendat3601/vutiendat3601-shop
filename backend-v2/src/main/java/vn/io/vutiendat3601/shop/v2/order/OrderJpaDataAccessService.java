package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  public Page<Order> selectAllByOrderByCreatedAtDesc(int page, int size) {
    return orderRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Page<Order> selectAllByCustomerCode(String customerCode, int page, int size) {
    return orderRepo.findAllByCustomerCode(customerCode, PageRequest.of(page, size));
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

  @Override
  @NonNull
  public Optional<Order> selectByTrackingNumberAndCustomerCode(
      @NonNull String trackingNumber, @NonNull String customerCode) {
    return orderRepo.findByTrackingNumberAndCustomerCode(trackingNumber, customerCode);
  }

  @Override
  public void update(@NonNull Order order) {
    orderRepo.save(order);
  }
}
