package vn.io.vutiendat3601.shop.v2.product;

import io.jsonwebtoken.lang.Assert;
import java.util.function.Function;

public class PriceHistoryDtoMapper implements Function<PriceHistory, PriceHistoryDto> {

  @Override
  public PriceHistoryDto apply(PriceHistory priceHistory) {
    Assert.notNull(priceHistory, "priceHistory argument must not be null");
    return new PriceHistoryDto(
        priceHistory.getId(), priceHistory.getPrice(), priceHistory.getProduct().getId());
  }
}
