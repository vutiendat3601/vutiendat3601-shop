package vn.io.vutiendat3601.shop.v2.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.auth.UserAuthentication;

@Service
public class JwtService {
  private final String USER_CLAIM = "user";
  private final String AUTHORITIES_CLAIM = "authorities";
  private final ObjectMapper objMapper;
  private final KeyPair KEY_RSA256;
  private final JwtParser JWT_PARSER;

  public JwtService() {
    KEY_RSA256 = Jwts.SIG.RS256.keyPair().build();
    JWT_PARSER = Jwts.parser().verifyWith(KEY_RSA256.getPublic()).build();
    objMapper = new ObjectMapper();
    objMapper.findAndRegisterModules();
  }

  @Value("${app.jwt.validDurationSecond}")
  private Integer jwtValidDurationSecond;

  public PublicKey getPublicKey() {
    return KEY_RSA256.getPublic();
  }

  public String generateJwt(
      @NonNull UserAuthentication userAuthentication, @NonNull List<String> authorities) {
    return Jwts.builder()
        .header()
        .and()
        .issuedAt(new Date())
        .subject(String.valueOf(userAuthentication.id()))
        .claim(USER_CLAIM, userAuthentication)
        .claim(AUTHORITIES_CLAIM, authorities)
        .signWith(KEY_RSA256.getPrivate())
        .compact();
  }

  public Claims validateJwt(String token) {
    final Jws<Claims> jwt = JWT_PARSER.parseSignedClaims(token);
    final Claims claims = jwt.getPayload();
    return claims;
  }

  public UserAuthentication parseUser(Claims claims) {
    try {
      final String serializedUserAuth = objMapper.writeValueAsString(claims.get(USER_CLAIM));
      return objMapper.readValue(serializedUserAuth, UserAuthentication.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Collection<? extends GrantedAuthority> parseAuthorites(Claims claims) {
    try {
      final String serializedAuthorities =
          objMapper.writeValueAsString(claims.get(AUTHORITIES_CLAIM));
      final List<String> authorities =
          objMapper.readValue(serializedAuthorities, new TypeReference<List<String>>() {});
      return authorities.stream().map(SimpleGrantedAuthority::new).toList();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
