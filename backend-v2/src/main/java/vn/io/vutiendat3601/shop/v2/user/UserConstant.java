package vn.io.vutiendat3601.shop.v2.user;

public interface UserConstant {
  User ROOT_USER =
      User.builder()
          .displayName("root")
          .email("vutien.dat.work@gmail.com")
          .isVerified(true)
          .build();
}
