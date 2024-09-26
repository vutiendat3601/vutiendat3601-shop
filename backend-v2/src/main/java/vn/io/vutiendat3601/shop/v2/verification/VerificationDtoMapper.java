package vn.io.vutiendat3601.shop.v2.verification;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import vn.io.vutiendat3601.shop.v2.user.User;

@Component
public class VerificationDtoMapper implements Function<Verification, VerificationDto> {
  @Override
  public VerificationDto apply(Verification verif) {
    Assert.notNull(verif, "verif argument must be not null");
    final User user = verif.getUser();
    Assert.notNull(user, "user attribute of verif argument must not be null");
    return new VerificationDto(
        verif.getCode(),
        verif.getExpiredAt(),
        verif.getIsDisabled(),
        verif.getType(),
        user.getId());
  }
}
