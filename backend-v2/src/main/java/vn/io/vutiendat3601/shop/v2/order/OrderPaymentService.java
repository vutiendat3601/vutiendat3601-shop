package vn.io.vutiendat3601.shop.v2.order;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.payment.PaymentMethod;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;
import vn.io.vutiendat3601.shop.v2.payment.VnPayPaymentProvider;
import vn.io.vutiendat3601.shop.v2.payment.VnPayPaymentResult;
import vn.io.vutiendat3601.shop.v2.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderPaymentService {
  private static final DateTimeFormatter VN_PAY_DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  private static final ZoneId VN_PAY_DEFAULT_ZONE_ID = ZoneId.of("GMT+7");

  @Value("${app.payment.vnpay.durationDay}")
  private Integer vnPayDurationDay;

  @Value("${app.payment.vnpay.vnpReturnUrl}")
  private String vnPayReturnUrl;

  private final OrderDao orderDao;

  private final OrderPaymentDao orderPaymentDao;

  private final OrderPaymentDtoMapper orderPaymentDtoMapper;

  @Qualifier("vnPayPaymentOrderRediectUrl")
  private final VnPayPaymentProvider vnPayPaymentProvider;

  public OrderPaymentDto createOrderPayment(
      @NonNull String trackingNumber,
      @NonNull CreateOrderPaymentRequest createOrderPaymentReq,
      @NonNull HttpServletRequest req) {
    final Order order =
        orderDao
            .selectByTrackingNumber(trackingNumber)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Order not found: (trackingNumber=%s)".formatted(trackingNumber)));
    final OrderStatus status = order.getStatus();
    if (OrderStatus.PENDING.equals(status)) {
      final OrderPayment orderPayment =
          generateOrderPayment(order, createOrderPaymentReq.method(), req);
      orderPaymentDao.insert(orderPayment);
      return orderPaymentDtoMapper.apply(orderPayment);
    }
    throw new ConflictException("Order status is not 'PENDING': (status=%s)".formatted(status));
  }

  public void updatePaymentStatus() {}

  private OrderPayment generateOrderPayment(
      Order order, PaymentMethod method, HttpServletRequest req) {
    final Map<String, String> params = new HashMap<>();
    final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    String ipAddr = req.getHeader("X-Forwarded-For");
    ipAddr = Objects.nonNull(ipAddr) ? ipAddr : "127.0.0.1";
    final String ref = StringUtils.makeRandomDigits(8);
    final BigDecimal amount = order.getFinalAmount();
    final String msg = "Thanh toan don hang tai shopsinhvien.io.vn: " + order.getTrackingNumber();
    final OrderPayment orderPayment =
        OrderPayment.builder()
            .ref(ref)
            .amount(amount)
            .clientIp(ipAddr)
            .message(msg)
            .status(PaymentStatus.PENDING)
            .order(order)
            .build();
    switch (method) {
      case VN_PAY:
        orderPayment.setPaymentUrlExpiredAt(now.plusDays(vnPayDurationDay));
        final ZonedDateTime vnPayCreatedDate = now.withZoneSameInstant(VN_PAY_DEFAULT_ZONE_ID);
        final ZonedDateTime vnPayExpireDate = vnPayCreatedDate.plusDays(vnPayDurationDay);
        params.put("vnp_Command", "pay");
        params.put("vnp_IpAddr", orderPayment.getClientIp());
        params.put("vnp_OrderInfo", msg);
        params.put("vnp_CreateDate", vnPayCreatedDate.format(VN_PAY_DATE_TIME_FORMATTER));
        params.put("vnp_ExpireDate", vnPayExpireDate.format(VN_PAY_DATE_TIME_FORMATTER));
        params.put("vnp_ReturnUrl", vnPayReturnUrl);
        final String paymentUrl =
            vnPayPaymentProvider.generatePaymentUrl(orderPayment.getAmount(), ref, params);
        orderPayment.setCallbackUrl(vnPayReturnUrl);
        orderPayment.setPaymentUrl(paymentUrl);
        orderPayment.setMethod(method);
        return orderPayment;
      default:
        throw new IllegalArgumentException("Payment method not found");
    }
  }

  public void processVnPayPaymentResult(@NonNull VnPayPaymentResult vnPayPaymentResult) {
    final Map<String, String> params =
        vnPayPaymentProvider.validateUrl(vnPayPaymentResult.redirectUrl());
    final OrderPayment orderPayment =
        orderPaymentDao
            .selectByRefAndStatus(params.get("vnp_TxnRef"), PaymentStatus.PENDING)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Order Payment not found: (ref=%s)".formatted(params.get("vnp_TxnRef"))));
    orderPayment.setStatus(PaymentStatus.SUCCESS);
    orderPayment.getOrder().setStatus(OrderStatus.PAID);
    orderPaymentDao.update(orderPayment);
  }
}
