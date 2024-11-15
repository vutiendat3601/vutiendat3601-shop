package vn.io.vutiendat3601.shop.v2.order;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.shop.v2.AbstractIntegrationTest;
import vn.io.vutiendat3601.shop.v2.auth.JwtDto;

public class OrderIntegrationTest extends AbstractIntegrationTest {
  private static final String ORDER_PATH = "/v2/orders";

  @Test
  void couldGetOrderPreview() {
    final JwtDto jwtDto = getToken();

    // Can get Order Preview
    final OrderPreviewRequest orderPreviewReq =
        new OrderPreviewRequest(
            1L, "USER_EMAIL", List.of(new CreateOrderItemDto("202409278514", 1, null)));

    webTestClient
        .mutate()
        .build()
        .post()
        .uri(ORDER_PATH + "/preview")
        .body(Mono.just(orderPreviewReq), OrderPreviewRequest.class)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtDto.token())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody();
  }
}
