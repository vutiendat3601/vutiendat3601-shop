package vn.io.vutiendat3601.shop.v2.address;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AddresDetailDtoMapper implements Function<AddressDetail, AddressDetailDto> {

  @Override
  public AddressDetailDto apply(AddressDetail addrDetail) {
    Assert.notNull(addrDetail, "addrDetail must not be null");
    return new AddressDetailDto(
        addrDetail.getCode(),
        addrDetail.getProvinceId(),
        addrDetail.getProvinceName(),
        addrDetail.getDistrictId(),
        addrDetail.getDistrictName(),
        addrDetail.getWardId(),
        addrDetail.getWardName(),
        addrDetail.getStreet(),
        addrDetail.getCustomerCode());
  }
}
