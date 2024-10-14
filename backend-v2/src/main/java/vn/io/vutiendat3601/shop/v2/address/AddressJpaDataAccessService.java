package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AddressJpaDataAccessService implements AddressDao {
  private final AddressRepository addrRepo;

  @Override
  public long insert(@NonNull Address addr) {
    return addrRepo.save(addr).getId();
  }

  @Override
  @NonNull
  public Optional<Address> selectByCodeAndCustomerCode(
      @NonNull String code, @NonNull String customerCode) {
    return addrRepo.findByCodeAndCustomerCode(code, customerCode);
  }

  @Override
  @NonNull
  public Optional<Address> selectById(long id) {
    return addrRepo.findById(id);
  }
}
