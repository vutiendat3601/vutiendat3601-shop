package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class WardDetailJpaDataAccessService implements WardDetailDao {
  private final WardDetailRepository wardDetailRepo;

  @Override
  @NonNull
  public Optional<WardDetail> selectById(long id) {
    return wardDetailRepo.findById(id);
  }
}
