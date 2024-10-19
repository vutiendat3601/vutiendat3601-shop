package vn.io.vutiendat3601.shop.v2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String JWT_REGEX = "^[A-Za-z0-9-_]+\\\\.[A-Za-z0-9-_]+\\\\.[A-Za-z0-9-_]+$";

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse resp,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
    String jwt = null;
    if (Objects.nonNull(authorization)
        && authorization.length() > 7
        && authorization.startsWith("Bearer ")
        && (jwt = authorization.substring(7)).matches(JWT_REGEX)) {
      final Authentication authResult =
          authenticationManager.authenticate(JwtAuthentication.unauthenticated(jwt));
      if (authResult.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authResult);
      }
    }
    filterChain.doFilter(req, resp);
  }
}
