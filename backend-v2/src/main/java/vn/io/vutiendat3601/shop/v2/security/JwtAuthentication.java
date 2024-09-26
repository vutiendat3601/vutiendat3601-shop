package vn.io.vutiendat3601.shop.v2.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import vn.io.vutiendat3601.shop.v2.auth.UserAuthentication;

public class JwtAuthentication implements Authentication {
  private String jwt;

  private UserAuthentication userAuth;

  private boolean isAuthenticated = false;

  private Collection<? extends GrantedAuthority> authorities;

  private JwtAuthentication() {}

  public static Authentication authenticated(
      String jwt, UserAuthentication userAuth, Collection<? extends GrantedAuthority> authorities) {
    final JwtAuthentication jwtAuth = new JwtAuthentication();
    jwtAuth.authorities = authorities;
    jwtAuth.isAuthenticated = true;
    jwtAuth.jwt = jwt;
    jwtAuth.userAuth = userAuth;
    return jwtAuth;
  }

  public static Authentication unauthenticated(String jwt) {
    final JwtAuthentication jwtAuth = new JwtAuthentication();
    jwtAuth.authorities = Collections.emptyList();
    jwtAuth.isAuthenticated = false;
    jwtAuth.jwt = jwt;
    return jwtAuth;
  }

  @Override
  public String getName() {
    return jwt;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Object getCredentials() {
    return jwt;
  }

  @Override
  public Object getDetails() {
    return userAuth;
  }

  @Override
  public Object getPrincipal() {
    return jwt;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    throw new IllegalArgumentException("Can't manually set authentication status");
  }
}
