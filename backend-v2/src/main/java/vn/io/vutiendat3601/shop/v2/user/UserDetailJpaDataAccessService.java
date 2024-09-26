package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserDetailJpaDataAccessService implements UserDetailDao {
  private final UserDetailRepository userDetailRepo;

  @Override
  @NonNull
  public Optional<UserDetail> selectById(long id) {
    return userDetailRepo.findById(id);
  }
}
