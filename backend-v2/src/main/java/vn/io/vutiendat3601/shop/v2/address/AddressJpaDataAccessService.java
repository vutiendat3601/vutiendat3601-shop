package vn.io.vutiendat3601.shop.v2.address;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AddressJpaDataAccessService implements AddressDao {
  private final AddressRepository addrRepo;

  @Override
  public void insert(@NonNull Address addr) {
    addrRepo.save(addr);
  }
}
