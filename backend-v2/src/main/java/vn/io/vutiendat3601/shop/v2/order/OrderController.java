package vn.io.vutiendat3601.shop.v2.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/orders")
public class OrderController {
  private final OrderService orderService;

  @PostMapping("preview")
  public ResponseEntity<OrderDto> createOrderPreview(
      @Validated @RequestBody CreateOrderRequest createOrderReq) {
    final OrderDto orderDto = orderService.getOrderPreview(createOrderReq);
    return ResponseEntity.ok(orderDto);
  }

  @PostMapping
  public ResponseEntity<?> createOrder(@Validated @RequestBody CreateOrderRequest createOrderReq) {
    orderService.createOrder(createOrderReq);
    return ResponseEntity.ok().build();
  }
}
