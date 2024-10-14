package vn.io.vutiendat3601.shop.v2.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@Tag(name = "Order Admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/admin/orders")
public class OrderAdminController {
  private final OrderService orderService;
  // private final OrderPaymentService orderPaymentService;

  @GetMapping
  public ResponseEntity<PageDto<OrderDto>> getOrders(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "100") Integer size) {
    final PageDto<OrderDto> orderDtoPage = orderService.getOrders(page, size);
    return ResponseEntity.ok(orderDtoPage);
  }
}
