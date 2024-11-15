package vn.io.vutiendat3601.shop.v2;

import static vn.io.vutiendat3601.shop.v2.common.IntegrationTestConstant.JWT_DTO_TYPEREF;
import static vn.io.vutiendat3601.shop.v2.common.IntegrationTestConstant.VERIFICATION_DTO_TYPEREF;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.BASE64_ENCODER;
import static vn.io.vutiendat3601.shop.v2.util.TestUtils.FAKER;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.shop.v2.auth.JwtDto;
import vn.io.vutiendat3601.shop.v2.customer.CreateCustomerRequest;
import vn.io.vutiendat3601.shop.v2.user.CreateUserRequest;
import vn.io.vutiendat3601.shop.v2.verification.VerificationDto;

@Configuration
@DirtiesContext
@RequiredArgsConstructor
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {"spring.docker.compose.skip.in-tests=false"})
public abstract class AbstractIntegrationTest {
  public static String USER_EMAIL;
  public static String USER_USERNAME = FAKER.internet().username();
  public static final String USER_PASSWORD = "$2024Vutiendat3601";

  @Autowired protected WebTestClient webTestClient;

  public JwtDto getToken() {
    // Login, expected LOGIN token in body
    final String encEmailPassword =
        BASE64_ENCODER.encodeToString("%s:%s".formatted(USER_EMAIL, USER_PASSWORD).getBytes());
    final VerificationDto verifDto =
        webTestClient
            .mutate()
            .responseTimeout(Duration.ofMillis(300_000))
            .build()
            .post()
            .uri("/v2/auth/login")
            .header(HttpHeaders.AUTHORIZATION, "Basic " + encEmailPassword)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(VERIFICATION_DTO_TYPEREF)
            .returnResult()
            .getResponseBody();

    // Get token
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", verifDto == null ? "" : verifDto.code());
    return webTestClient
        .post()
        .uri("/v2/auth/token")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromFormData(formData))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(JWT_DTO_TYPEREF)
        .returnResult()
        .getResponseBody();
  }

  @BeforeEach
  void generateUser() {
    USER_EMAIL = UUID.randomUUID() + FAKER.internet().emailAddress();
    USER_USERNAME = UUID.randomUUID() + FAKER.internet().username();
    // Create User
    final CreateUserRequest createUserReq =
        new CreateUserRequest(USER_USERNAME, USER_EMAIL, USER_PASSWORD);
    webTestClient
        .mutate()
        .responseTimeout(Duration.ofMillis(300_000))
        .build()
        .post()
        .uri("/v2/auth/sign-up")
        .body(Mono.just(createUserReq), CreateCustomerRequest.class)
        .exchange()
        .expectStatus()
        .isOk();

    // Login, expected LOGIN token in body
    final String encEmailPassword =
        BASE64_ENCODER.encodeToString(
            "%s:%s".formatted(createUserReq.email(), createUserReq.password()).getBytes());
    webTestClient
        .mutate()
        .responseTimeout(Duration.ofMillis(300_000))
        .build()
        .post()
        .uri("/v2/auth/login")
        .header(HttpHeaders.AUTHORIZATION, "Basic " + encEmailPassword)
        .exchange()
        .expectStatus()
        .isOk();
  }
}
