package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;

public record OrderPaymentDto(
    String trackingNumber,
    String ref,
    BigDecimal amount,
    PaymentStatus status,
    String message,
    String errorMessage,
    String paymentUrl,
    ZonedDateTime paymentUrlExpiredAt,
    String callbackUrl) {}
