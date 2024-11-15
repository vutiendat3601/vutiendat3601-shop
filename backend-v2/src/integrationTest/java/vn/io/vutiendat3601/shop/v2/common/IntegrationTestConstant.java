package vn.io.vutiendat3601.shop.v2.common;

import org.springframework.core.ParameterizedTypeReference;
import vn.io.vutiendat3601.shop.v2.auth.JwtDto;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

public interface IntegrationTestConstant {
  ParameterizedTypeReference<VerificationDto> VERIFICATION_DTO_TYPEREF =
      new ParameterizedTypeReference<>() {};

  ParameterizedTypeReference<JwtDto> JWT_DTO_TYPEREF = new ParameterizedTypeReference<JwtDto>() {};
}
