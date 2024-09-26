package vn.io.vutiendat3601.shop.v2.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProvinceJpaDataAccessService implements ProvinceDao {
  private final ProvinceRepository provinceRepo;
  @Override
  public Page<Province> selectAll(int page, int size) {
    return provinceRepo.findAll(PageRequest.of(page, size));
  }
}
