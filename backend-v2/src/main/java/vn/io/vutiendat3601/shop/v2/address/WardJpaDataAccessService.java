package vn.io.vutiendat3601.shop.v2.address;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WardJpaDataAccessService implements WardDao {
  private final WardRepository wardRepo;

  @Override
  @NonNull
  public Page<Ward> selectAllByDistrictId(long distId, int page, int size) {
    return wardRepo.findAllByDistrictId(distId, PageRequest.of(page, size));
  }

  @Override
  public boolean existsById(long wardId) {
    return wardRepo.existsById(wardId);
  }

  @Override
  @NonNull
  public Optional<Ward> selectById(long id) {
    return wardRepo.findById(id);
  }
}
