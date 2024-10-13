package vn.io.vutiendat3601.shop.v2.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.payment.VnPayPaymentResult;

@Tag(name = "Order")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderPaymentService orderPaymentService;

  @PostMapping("preview")
  public ResponseEntity<OrderDto> createOrderPreview(
      @Validated @RequestBody CreateOrderRequest createOrderReq) {
    final OrderDto orderDto = orderService.getOrderPreview(createOrderReq);
    return ResponseEntity.ok(orderDto);
  }

  @PostMapping
  public ResponseEntity<CreatedOrderDto> createOrder(
      @Validated @RequestBody CreateOrderRequest createOrderReq) {
    final CreatedOrderDto createdOrderDto = orderService.createOrder(createOrderReq);
    return ResponseEntity.ok(createdOrderDto);
  }

  @PostMapping("{trackingNumber}/payment")
  public ResponseEntity<OrderPaymentDto> createOrderPayment(
      @PathVariable(name = "trackingNumber") String trackingNumber,
      @RequestBody CreateOrderPaymentRequest createOrderPaymentReq,
      HttpServletRequest req) {
    final OrderPaymentDto orderPaymentRedirectUrlDto =
        orderPaymentService.createOrderPayment(trackingNumber, createOrderPaymentReq, req);
    return ResponseEntity.ok(orderPaymentRedirectUrlDto);
  }

  @PostMapping("payment/vnpay")
  public ResponseEntity<?> processOrderPaymentResult(
      @RequestBody VnPayPaymentResult vnPayPaymentResult) {
    orderPaymentService.processVnPayPaymentResult(vnPayPaymentResult);
    return ResponseEntity.ok().build();
  }
}
