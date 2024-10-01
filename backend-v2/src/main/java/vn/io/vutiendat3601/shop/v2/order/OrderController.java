package vn.io.vutiendat3601.shop.v2.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v2/orders")
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<?> createOrder(@Validated @RequestBody CreateOrderRequest createOrderReq) {
    orderService.createOrder(createOrderReq);
    return ResponseEntity.ok().build();
  }
}
