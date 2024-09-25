package vn.io.vutiendat3601.shop.v2.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  private static final String[] PUBLIC_GET_ROUTES = {
    "/v2/auth/login", "/v2/apidocs*/**", "/v2/swagger-ui/**"
  };

  private static final String[] PUBLIC_POST_ROUTES = {"/v2/auth/sign-up"};

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            reqs -> {
              reqs.requestMatchers(GET, PUBLIC_GET_ROUTES).permitAll();
              reqs.requestMatchers(POST, PUBLIC_POST_ROUTES).permitAll();
            })
        .csrf(csrf -> csrf.disable())
        .httpBasic(hb -> hb.disable())
        .sessionManagement(ssmgnt -> ssmgnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(BCryptVersion.$2B);
  }
}
