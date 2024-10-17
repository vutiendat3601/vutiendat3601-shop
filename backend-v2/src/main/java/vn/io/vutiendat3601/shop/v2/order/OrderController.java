package vn.io.vutiendat3601.shop.v2.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.payment.VnPayPaymentResult;

@CrossOrigin
@Tag(name = "Order")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderPaymentService orderPaymentService;

  @PostMapping("preview")
  public ResponseEntity<OrderDto> createOrderPreview(
      @Validated @RequestBody OrderPreviewRequest orderPreviewReq) {
    final OrderDto orderDto = orderService.getOrderPreview(orderPreviewReq);
    return ResponseEntity.ok(orderDto);
  }

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(
      @Validated @RequestBody CreateOrderRequest createOrderReq) {
    final OrderDto orderDto = orderService.createOrder(createOrderReq);
    return ResponseEntity.ok(orderDto);
  }

  @GetMapping
  public ResponseEntity<PageDto<OrderDto>> getOrdersByCurrentUser(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "100") Integer size) {
    final PageDto<OrderDto> orderDtoPage = orderService.getOrdersByCurrentUser(page, size);
    return ResponseEntity.ok(orderDtoPage);
  }

  @GetMapping("{trackingNumber}")
  public ResponseEntity<OrderDto> getOrderByCurrentUser(
      @PathVariable(name = "trackingNumber") String trackingNumber) {
    final OrderDto orderDto = orderService.getOrderByCurrentUser(trackingNumber);
    return ResponseEntity.ok(orderDto);
  }

  @PostMapping("{trackingNumber}/payment")
  public ResponseEntity<OrderPaymentDto> createOrderPayment(
      @PathVariable(name = "trackingNumber") String trackingNumber,
      @RequestBody CreateOrderPaymentRequest createOrderPaymentReq,
      HttpServletRequest req) {
    final OrderPaymentDto orderPaymentDto =
        orderPaymentService.createOrderPayment(trackingNumber, createOrderPaymentReq, req);
    return ResponseEntity.ok(orderPaymentDto);
  }

  @PostMapping("payment/vnpay")
  public ResponseEntity<?> processOrderPaymentResult(
      @RequestBody VnPayPaymentResult vnPayPaymentResult) {
    orderPaymentService.processVnPayPaymentResult(vnPayPaymentResult);
    return ResponseEntity.ok().build();
  }
}
