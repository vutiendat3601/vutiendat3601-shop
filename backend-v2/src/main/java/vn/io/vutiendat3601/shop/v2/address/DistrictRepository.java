package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface DistrictRepository extends JpaRepository<District, Long> {
  @NonNull
  Page<District> findAllByProvinceId(long provinceId, Pageable pageable);
}
