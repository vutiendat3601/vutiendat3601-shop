package vn.io.vutiendat3601.shop.v2.auth;

public interface AuthConstant {
  String BASIC_AUTH_PREFIX = "Basic ";
  UserAuthentication ROOT_USER_AUTHENTICATION =
      new UserAuthentication(0L, "root", "root", true, "2c0e3d09-8dd1-4d26-adbe-0310cf513d2d");
}
