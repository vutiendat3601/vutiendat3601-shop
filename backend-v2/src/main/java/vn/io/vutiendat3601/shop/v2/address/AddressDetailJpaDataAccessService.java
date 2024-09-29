package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AddressDetailJpaDataAccessService implements AddressDetailDao {
  private final AddressDetailRepository addrDetailRepo;

  @Override
  @NonNull
  public Page<AddressDetail> selectAllByCustomerId(long customerId, int page, int size) {
    return addrDetailRepo.findAllByCustomerId(customerId, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Optional<AddressDetail> selectById(@NonNull Long id) {
    return addrDetailRepo.findById(id);
  }

  @Override
  @NonNull
  public Optional<AddressDetail> selectByCodeAndCustomerCode(@NonNull String code, @NonNull String customerCode) {
    return addrDetailRepo.findByCodeAndCustomerCode(code,customerCode);
  }
}
