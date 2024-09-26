package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DistrictJpaDataAccessService implements DistrictDao {
  private final DistrictRepository distRepo;

  @Override
  @NonNull
  public Page<District> selectAllByProvinceId(long provinceId, int page, int size) {
    return distRepo.findAllByProvinceId(provinceId, PageRequest.of(page, size));
  }
}
