package vn.io.vutiendat3601.shop.v2.verification;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.common.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.util.StringUtils;

@RequiredArgsConstructor
@Service
public class VerficationService {
  @Value("${app.verification.login.validDurationSecond}")
  private Long loginValidDurationSecond;

  @Value("${app.verification.email.validDurationSecond}")
  private Long emailValidDurationSecond;

  @Value("${app.verification.phone.validDurationSecond}")
  private Long phoneValidDurationSecond;

  @Value("${app.verification.password.validDurationSecond}")
  private Long passwordValidDurationSecond;

  @Value("${app.verification.login.codeLength}")
  private Integer loginCodeLength;

  @Value("${app.verification.email.codeLength}")
  private Integer emailCodeLength;

  @Value("${app.verification.phone.codeLength}")
  private Integer phoneCodeLength;

  @Value("${app.verification.password.codeLength}")
  private Integer passwordCodeLength;

  private final VerificationDao verifDao;

  private final VerificationDtoMapper verifDtoMapper;

  public void disableAllVerifications(long userId, VerificationType type) {
    final List<Verification> verifs =
        verifDao.selectAllByUserIdAndTypeAndIsDisabled(userId, type, false);
    verifs.forEach(verif -> verif.setIsDisabled(true));
    verifDao.update(verifs);
  }

  public void generateVerification(long userId, VerificationType type) {
    final Verification verif =
        Verification.builder()
            .user(new User(userId))
            .code(generateCode(type))
            .expiredAt(ZonedDateTime.now().plusSeconds(getValidDurationSecond(type)))
            .type(type)
            .build();
    verifDao.insert(verif);
  }

  public VerificationDto getLatestVerification(long userId, VerificationType type) {
    final Verification verif =
        verifDao
            .selectFirstByUserIdAndTypeOrderByCreatedAtDesc(userId, type)
            .orElseThrow(() -> new ResourceNotFoundException("No verification found"));
    return verifDtoMapper.apply(verif);
  }

  private long getValidDurationSecond(VerificationType type) {
    switch (type) {
      case EMAIL_VERIFICATION:
        return emailValidDurationSecond;
      case LOGIN_CODE:
        return loginValidDurationSecond;
      case PASSWORD_RESET:
        return passwordValidDurationSecond;
      case PHONE_VERIFICATION:
        return phoneValidDurationSecond;
      default:
        return 0;
    }
  }

  private String generateCode(VerificationType type) {
    switch (type) {
      case EMAIL_VERIFICATION:
        return StringUtils.makeRandomString(emailCodeLength);
      case LOGIN_CODE:
        return StringUtils.makeRandomString(loginCodeLength);
      case PASSWORD_RESET:
        return StringUtils.makeRandomString(passwordCodeLength);
      case PHONE_VERIFICATION:
        return StringUtils.makeRandomDigits(phoneCodeLength);
      default:
        return "";
    }
  }
}
