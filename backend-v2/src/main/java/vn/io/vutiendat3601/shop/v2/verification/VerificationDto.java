package vn.io.vutiendat3601.shop.v2.verification;

import java.time.ZonedDateTime;

public record VerificationDto(
    String code, ZonedDateTime expiredAt, Boolean isDisabled, VerificationType type, Long userId) {}
