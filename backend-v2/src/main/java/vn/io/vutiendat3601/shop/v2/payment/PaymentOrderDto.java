package vn.io.vutiendat3601.shop.v2.payment;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record PaymentOrderDto(
    String paymentUrl, BigDecimal amount, String orderTrackingNumber, ZonedDateTime expiredDate) {}
