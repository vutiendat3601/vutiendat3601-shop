package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AddressDetailJpaDataAccessService implements AddressDetailDao {
  private final AddressDetailRepository addressDetailRepo;

  @Override
  @NonNull
  public Page<AddressDetail> selectAllByCustomerId(long customerId, int page, int size) {
    return addressDetailRepo.findAllByCustomerId(customerId, PageRequest.of(page, size));
  }
}
