package vn.io.vutiendat3601.shop.v2.auth;

import static vn.io.vutiendat3601.shop.v2.auth.AuthConstant.BASIC_AUTH_PREFIX;
import static vn.io.vutiendat3601.shop.v2.verification.VerificationType.LOGIN_CODE;

import java.util.Base64;
import java.util.Base64.Decoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.io.vutiendat3601.shop.v2.notification.EmailAsyncService;
import vn.io.vutiendat3601.shop.v2.user.CreateUserRequest;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserDao;
import vn.io.vutiendat3601.shop.v2.user.UserService;
import vn.io.vutiendat3601.shop.v2.verification.VerficationService;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

@RequiredArgsConstructor
@Service
public class AuthService {
  private final Decoder BASE64_DECODER = Base64.getDecoder();

  private final UserDao userDao;
  private final PasswordEncoder passEncoder;
  private final VerficationService verifService;
  private final UserService userService;
  private final EmailAsyncService emailAsyncService;

  public VerificationDto loginByEmailAndPassword(String basicAuth) {
    final String decodedBasicAuth = decodeBasicAuth(basicAuth);
    final int colonIndex = decodedBasicAuth.indexOf(':');
    final String email = decodedBasicAuth.substring(0, colonIndex);
    final String password = decodedBasicAuth.substring(colonIndex + 1);
    final User user =
        userDao
            .selectByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    boolean isPasswordMatched = passEncoder.matches(password, user.getHashedPassword());
    if (isPasswordMatched) {
      verifService.disableAllVerifications(user.getId(), LOGIN_CODE);
      verifService.generateVerification(user.getId(), LOGIN_CODE);
      return verifService.getLatestVerification(user.getId(), LOGIN_CODE);
    }
    throw new BadCredentialsException("Wrong credentials");
  }

  public void signUp(CreateUserRequest createUserReq) {
    userService.createUser(createUserReq);
    emailAsyncService.sendEmail(
        createUserReq.email(), "Email Verification", "<h1>Please verification your email</h1>");
  }

  private String decodeBasicAuth(String basicAuth) {
    if (basicAuth.startsWith(BASIC_AUTH_PREFIX)) {
      basicAuth = basicAuth.replace(BASIC_AUTH_PREFIX, "");
    }
    return new String(BASE64_DECODER.decode(basicAuth));
  }
}
