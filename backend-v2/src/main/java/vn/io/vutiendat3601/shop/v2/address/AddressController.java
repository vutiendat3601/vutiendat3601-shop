package vn.io.vutiendat3601.shop.v2.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("v2/addresses")
public class AddressController {
  private final AddressService addrService;

  @GetMapping("provinces")
  public ResponseEntity<PageDto<ProvinceDto>> getProvinces(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size) {
    final PageDto<ProvinceDto> provinceDtoPage = addrService.getProvinces(page, size);
    return ResponseEntity.ok(provinceDtoPage);
  }

  @GetMapping("provinces/{provinceId}/districts")
  public ResponseEntity<PageDto<DistrictDto>> getDistricts(
      @PathVariable Long provinceId,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size) {
    final PageDto<DistrictDto> distDtoPage = addrService.getDistricts(provinceId, page, size);
    return ResponseEntity.ok(distDtoPage);
  }

  @GetMapping("districts/{districtId}/wards")
  public ResponseEntity<PageDto<WardDto>> getWards(
      @PathVariable Long districtId,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size) {
    final PageDto<WardDto> wardDtoPage = addrService.getWards(districtId, page, size);
    return ResponseEntity.ok(wardDtoPage);
  }

  @PostMapping
  public ResponseEntity<AddressDetailDto> createAddress(
      @RequestBody CreateAddressRequest createAddrReq) {
    final AddressDetailDto addrDetailDto = addrService.createAddress(createAddrReq);
    return ResponseEntity.ok(addrDetailDto);
  }
}
