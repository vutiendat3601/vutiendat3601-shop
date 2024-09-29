package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AddressRepository extends JpaRepository<Address, Long> {
  @NonNull
  Optional<Address> findByCodeAndCustomerCode(@NonNull String code, @NonNull String customerCode);
}
