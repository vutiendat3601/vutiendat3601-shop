package vn.io.vutiendat3601.shop.v2.auth;

import static vn.io.vutiendat3601.shop.v2.common.TestConstant.JWT_DTO_TYPEREF;
import static vn.io.vutiendat3601.shop.v2.common.TestConstant.VERIFICATION_DTO_TYPEREF;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.BASE64_ENCODER;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.shop.v2.customer.CreateCustomerRequest;
import vn.io.vutiendat3601.shop.v2.user.CreateUserRequest;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {"spring.docker.compose.skip.in-tests=false"})
public class AuthItegrationTest {
  private static final String AUTH_PATH = "/v2/auth";

  private WebTestClient webTestClient;

  @Autowired
  public AuthItegrationTest(WebTestClient webTestClient, Environment env) {
    this.webTestClient = webTestClient;
  }

  @Test
  void couldSignUp() {
    // Create User
    final CreateUserRequest createUserReq =
        new CreateUserRequest(
            FAKER.internet().username(), FAKER.internet().emailAddress(), "123456Aa@$");
    webTestClient
        .mutate()
        .responseTimeout(Duration.ofMillis(300_000))
        .build()
        .post()
        .uri(AUTH_PATH + "/sign-up")
        .body(Mono.just(createUserReq), CreateCustomerRequest.class)
        .exchange()
        .expectStatus()
        .isOk();

    // Login, expected LOGIN token in body
    final String encEmailPassword =
        BASE64_ENCODER.encodeToString(
            "%s:%s".formatted(createUserReq.email(), createUserReq.password()).getBytes());
    final EntityExchangeResult<VerificationDto> loginResp =
        webTestClient
            .mutate()
            .responseTimeout(Duration.ofMillis(300_000))
            .build()
            .post()
            .uri(AUTH_PATH + "/login")
            .header(HttpHeaders.AUTHORIZATION, "Basic " + encEmailPassword)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(VERIFICATION_DTO_TYPEREF)
            .returnResult();

    // Get token
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    final VerificationDto loginRespBody = loginResp.getResponseBody();
    formData.add("code", loginRespBody == null ? "" : loginRespBody.code());
    webTestClient
        .post()
        .uri(AUTH_PATH + "/token")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromFormData(formData))
        // .body(Mono.just(tokenReq), TokenRequest.class)
        // .formData(form -> form)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(JWT_DTO_TYPEREF);
  }
}
