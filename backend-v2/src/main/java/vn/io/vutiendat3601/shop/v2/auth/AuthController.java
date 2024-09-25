package vn.io.vutiendat3601.shop.v2.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.user.CreateUserRequest;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("v2/auth")
public class AuthController {
  private final AuthService authService;

  @GetMapping("login")
  public ResponseEntity<VerificationDto> login(
      @RequestHeader("Authorization") String authorization) {
    final VerificationDto verifDto = authService.loginByEmailAndPassword(authorization);
    return ResponseEntity.ok(verifDto);
  }

  @PostMapping("sign-up")
  public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserRequest createUserReq) {
    authService.signUp(createUserReq);
    return ResponseEntity.ok().build();
  }
}
