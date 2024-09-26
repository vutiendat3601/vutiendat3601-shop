package vn.io.vutiendat3601.shop.v2.security;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.shop.v2.auth.UserAuthentication;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
  private final JwtService jwtService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String jwt = authentication.getName();
    final Claims claims = jwtService.validateJwt(jwt);
    final UserAuthentication userAuth = jwtService.parseUser(claims);
    final Collection<? extends GrantedAuthority> authorities = jwtService.parseAuthorites(claims);
    return JwtAuthentication.authenticated(jwt, userAuth, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(JwtAuthentication.class);
  }
}
