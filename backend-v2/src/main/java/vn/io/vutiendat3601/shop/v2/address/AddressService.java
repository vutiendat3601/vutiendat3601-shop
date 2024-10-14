package vn.io.vutiendat3601.shop.v2.address;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.customer.CustomerDao;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;

@RequiredArgsConstructor
@Service
public class AddressService {
  private final AddressDao addrDao;
  private final AddressDetailDao addrDetailDao;
  private final ProvinceDao provinceDao;
  private final DistrictDao distDao;
  private final WardDao wardDao;
  private final CustomerDao customerDao;
  private final AuthContext authContext;
  private final AddresDetailDtoMapper addrDetailDtoMapper;

  private final ProvinceDtoMapper provinceDtoMapper;
  private final DistrictDtoMapper distDtoMapper;
  private final WardDtoMapper wardDtoMapper;

  public PageDto<AddressDetailDto> getAddressDetails(long customerId, int page, int size) {
    page--;
    final Page<AddressDetail> addrDetailPage =
        addrDetailDao.selectAllByCustomerId(customerId, page, size);
    return PageDto.of(addrDetailPage).map(addrDetailDtoMapper);
  }

  public PageDto<ProvinceDto> getProvinces(int page, int size) {
    page--;
    final Page<Province> provincePage = provinceDao.selectAll(page, size);
    return PageDto.of(provincePage).map(provinceDtoMapper);
  }

  public PageDto<DistrictDto> getDistricts(long provinceId, int page, int size) {
    page--;
    final Page<District> distPage = distDao.selectAllByProvinceId(provinceId, page, size);
    return PageDto.of(distPage).map(distDtoMapper);
  }

  public PageDto<WardDto> getWards(long distId, int page, int size) {
    page--;
    final Page<Ward> distPage = wardDao.selectAllByDistrictId(distId, page, size);
    return PageDto.of(distPage).map(wardDtoMapper);
  }

  public AddressDetailDto createAddress(CreateAddressRequest createAddrReq) {
    Address addr = new Address();
    if (!wardDao.existsById(createAddrReq.wardId())) {
      throw new ResourceNotFoundException("Ward not found");
    }
    addr.setStreet(createAddrReq.street());
    addr.setWard(new Ward(createAddrReq.wardId()));
    final String customerCode = authContext.getUser().customerCode();
    final Customer customer =
        customerDao
            .selectByCode(authContext.getUser().customerCode())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Customer not found: (code=%s)".formatted(customerCode)));
    addr.setCustomer(customer);
    long id = addrDao.insert(addr);
    final AddressDetail addrDetail =
        addrDetailDao
            .selectById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    return addrDetailDtoMapper.apply(addrDetail);
  }
}
