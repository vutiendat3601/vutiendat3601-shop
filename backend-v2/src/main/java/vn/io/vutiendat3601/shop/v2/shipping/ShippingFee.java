package vn.io.vutiendat3601.shop.v2.shipping;

import java.math.BigDecimal;
import java.util.Set;

public record ShippingFee(Double km, BigDecimal unitPrice, Set<Long> provinceIds) {}
