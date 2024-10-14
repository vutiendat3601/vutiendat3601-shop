package vn.io.vutiendat3601.shop.v2.address;

public record AddressDetailDto(
    String code,
    Long provinceId,
    String provinceName,
    Long districtId,
    String districtName,
    Long wardId,
    String wardName,
    String street,
    String customerCode) {}
