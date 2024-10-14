package vn.io.vutiendat3601.shop.v2.auth;

import static vn.io.vutiendat3601.shop.v2.auth.AuthConstant.BASIC_AUTH_PREFIX;
import static vn.io.vutiendat3601.shop.v2.verification.VerificationType.LOGIN_CODE;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.customer.CreateCustomerRequest;
import vn.io.vutiendat3601.shop.v2.customer.CustomerService;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.notification.EmailAsyncService;
import vn.io.vutiendat3601.shop.v2.security.JwtService;
import vn.io.vutiendat3601.shop.v2.user.CreateUserRequest;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserDao;
import vn.io.vutiendat3601.shop.v2.user.UserDetail;
import vn.io.vutiendat3601.shop.v2.user.UserDetailDao;
import vn.io.vutiendat3601.shop.v2.user.UserDto;
import vn.io.vutiendat3601.shop.v2.user.UserService;
import vn.io.vutiendat3601.shop.v2.verification.VerficationService;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

@RequiredArgsConstructor
@Service
public class AuthService {
  private final Decoder BASE64_DECODER = Base64.getDecoder();

  @Value("${app.frontendUrl}")
  private String frontendUrl;

  private final JwtService jwtService;
  private final UserDao userDao;
  private final CustomerService customerService;
  private final UserDetailDao userDetailDao;
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
      verifService.createVerification(user.getId(), LOGIN_CODE);
      return verifService.getLatestVerification(user.getId(), LOGIN_CODE);
    }
    throw new BadCredentialsException("Wrong credentials");
  }

  public void signUp(CreateUserRequest createUserReq) {
    userService.createUser(createUserReq);
    final UserDto userDto = userService.getUserByUsername(createUserReq.username());
    final String phone = userDto.phone();
    final List<String> phones = Objects.isNull(phone) ? List.of() : List.of(phone);
    final CreateCustomerRequest createCustomerReq =
        new CreateCustomerRequest(userDto.id(), userDto.username(), phones);
    customerService.createCustomer(createCustomerReq);
    verifService.createVerification(userDto.id(), LOGIN_CODE);
    final VerificationDto verifDto = verifService.getLatestVerification(userDto.id(), LOGIN_CODE);
    final String emailVerificaitionHtml =
        """
        <a href="%s/email-verification?code=%s">Click here to verify email</a>
        """
            .formatted(frontendUrl, verifDto.code());
    emailAsyncService.sendHtmlMail(
        createUserReq.email(), "Email Verification", emailVerificaitionHtml);
  }

  public JwtDto generateJwt(@NonNull TokenRequest tokenReq) {
    final BadCredentialsException wrongCodeException = new BadCredentialsException("Wrong code");
    final VerificationDto verifDto = verifService.getVerfication(tokenReq.code());
    if (verifDto.isDisabled()) {
      throw wrongCodeException;
    }
    verifService.disableVerification(tokenReq.code());
    if (!LOGIN_CODE.equals(verifDto.type()) || verifDto.expiredAt().isBefore(ZonedDateTime.now())) {
      throw wrongCodeException;
    }

    final UserDetail userDetail =
        userDetailDao
            .selectById(verifDto.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    final UserAuthentication userAuth =
        new UserAuthentication(
            userDetail.getId(),
            userDetail.getUsername(),
            userDetail.getName(),
            userDetail.getIsVerified(),
            userDetail.getCustomerCode());
    final String jwt = jwtService.generateJwt(userAuth, userDetail.getAuthorities());
    return new JwtDto(jwt);
  }

  private String decodeBasicAuth(String basicAuth) {
    if (basicAuth.startsWith(BASIC_AUTH_PREFIX)) {
      basicAuth = basicAuth.replace(BASIC_AUTH_PREFIX, "");
    }
    return new String(BASE64_DECODER.decode(basicAuth));
  }
}
