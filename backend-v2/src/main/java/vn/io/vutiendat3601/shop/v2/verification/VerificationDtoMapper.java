package vn.io.vutiendat3601.shop.v2.verification;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class VerificationDtoMapper implements Function<Verification, VerificationDto> {
  @Override
  public VerificationDto apply(Verification verif) {
    return new VerificationDto(
        verif.getCode(), verif.getExpiredAt(), verif.getIsDisabled(), verif.getType());
  }
}
