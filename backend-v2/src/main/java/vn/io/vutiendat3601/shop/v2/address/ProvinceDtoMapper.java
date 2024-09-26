package vn.io.vutiendat3601.shop.v2.address;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProvinceDtoMapper implements Function<Province, ProvinceDto> {
  @Override
  public ProvinceDto apply(Province province) {
    Assert.notNull(province, "province argument must not be null");
    return new ProvinceDto(
        province.getId(), province.getName(), province.getCreatedAt(), province.getUpdatedAt());
  }
}
