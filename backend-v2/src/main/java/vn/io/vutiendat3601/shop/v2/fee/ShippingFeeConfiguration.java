package vn.io.vutiendat3601.shop.v2.fee;

import java.math.BigDecimal;
import java.util.List;

public record ShippingFeeConfiguration(
    List<ShippingFee> shippingFees, BigDecimal defaultFee, String description) {}
