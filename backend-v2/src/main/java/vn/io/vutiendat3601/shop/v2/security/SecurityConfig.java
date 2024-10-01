package vn.io.vutiendat3601.shop.v2.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
  private static final String[] PUBLIC_GET_ROUTES = {
    "/v2/apidocs*/**",
    "/v2/swagger-ui/**",
    "/v2/auth/**",
    "/v2/addresses/provinces",
    "/v2/addresses/provinces/{provinceId}/districts",
    "/v2/addresses/districts/{districtId}/wards",
    "/v2/products",
    "/v2/products/**",
    "/v2/categories/{categoryId}/products",
    "/v2/categories",
    "/v2/categories/**"
  };

  private static final String[] PUBLIC_POST_ROUTES = {"/v2/auth/**"};

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      AuthenticationManager authenticationManager,
      CorsConfigurationSource corsConfigSrouce)
      throws Exception {
    return http.authorizeHttpRequests(
            reqs ->
                reqs.requestMatchers(GET, PUBLIC_GET_ROUTES)
                    .permitAll()
                    .requestMatchers(POST, PUBLIC_POST_ROUTES)
                    .permitAll()
                    .anyRequest()
                    .permitAll())
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigSrouce))
        .anonymous(anonymous -> anonymous.disable())
        .authenticationManager(authenticationManager)
        .addFilterAfter(
            new JwtAuthenticationFilter(authenticationManager), SecurityContextHolderFilter.class)
        .sessionManagement(ssmgnt -> ssmgnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  @Bean
  AuthenticationManager authenticationManager(JwtAuthenticationProvider authenticationProvider) {
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(BCryptVersion.$2B);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    return req -> {
      final CorsConfiguration corsConfig = new CorsConfiguration();
      corsConfig.addAllowedOrigin("*");
      corsConfig.addAllowedMethod("*");
      corsConfig.addAllowedHeader("*");
      return corsConfig;
    };
  }
}
