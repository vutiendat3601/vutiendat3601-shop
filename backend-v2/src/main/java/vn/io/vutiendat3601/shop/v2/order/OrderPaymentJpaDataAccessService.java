package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderPaymentJpaDataAccessService implements OrderPaymentDao {
  private final OrderPaymentRepository orderPaymentRepo;


  @Override
  @NonNull
  public Optional<OrderPayment> selectByRefAndStatus(@NonNull String ref, @NonNull PaymentStatus status) {
    return orderPaymentRepo.findByRefAndStatus(ref,status);
  }

  @Override
  @NonNull
  public Optional<OrderPayment> selectByRef(@NonNull String ref) {
    return orderPaymentRepo.findByRef(ref);
  }

  @Override
  public void insert(@NonNull OrderPayment orderPayment) {
    orderPaymentRepo.save(orderPayment);
  }

  @Override
  public void update(@NonNull OrderPayment orderPayment) {
    orderPaymentRepo.save(orderPayment);
  }

}
