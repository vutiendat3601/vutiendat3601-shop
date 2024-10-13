package vn.io.vutiendat3601.shop.v2.order;

import vn.io.vutiendat3601.shop.v2.payment.PaymentMethod;

public record CreateOrderPaymentRequest(PaymentMethod method) {}
