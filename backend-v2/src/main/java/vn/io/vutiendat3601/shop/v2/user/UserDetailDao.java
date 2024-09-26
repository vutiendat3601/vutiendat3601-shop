package vn.io.vutiendat3601.shop.v2.user;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface UserDetailDao {
  @NonNull
  Optional<UserDetail> selectById(long id);
}
