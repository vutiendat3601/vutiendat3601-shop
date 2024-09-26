package vn.io.vutiendat3601.shop.v2.auth;

import static vn.io.vutiendat3601.shop.v2.auth.AuthConstant.ROOT_USER_AUTHENTICATION;

import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {
  public UserAuthentication getUser() {
    final SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    if (Objects.nonNull(authentication)
        && authentication.getDetails() instanceof UserAuthentication userAuth) {
      return userAuth;
    }
    return ROOT_USER_AUTHENTICATION;
  }
}
