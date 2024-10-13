package vn.io.vutiendat3601.shop.v2.fee;

import static vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationValueType.JSON;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConstant.KEY_SHIPPING_FEE;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.address.AddressDetail;
import vn.io.vutiendat3601.shop.v2.address.AddressDetailDao;
import vn.io.vutiendat3601.shop.v2.address.WardDetail;
import vn.io.vutiendat3601.shop.v2.address.WardDetailDao;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfiguration;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationDao;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.MissingConfigurationException;
import vn.io.vutiendat3601.shop.v2.exception.WrongConfigurationException;
import vn.io.vutiendat3601.shop.v2.order.Order;
import vn.io.vutiendat3601.shop.v2.util.ObjectMapperUtils;

@RequiredArgsConstructor
@Service
public class KmShippingFeeCaculator implements ShippingFeeCalculator {
  @Setter private ShippingFeeConfiguration shippingFeeConfiguration;
  private final BusinessConfigurationDao businessConfigDao;
  private final AddressDetailDao addrDetailDao;
  private final WardDetailDao wardDetailDao;

  @PostConstruct
  private void loadShippingFeeConfig() {
    final BusinessConfiguration shippingFeeBusinessConfig =
        businessConfigDao
            .selectByKeyAndValueType(KEY_SHIPPING_FEE, JSON)
            .orElseThrow(
                () ->
                    new MissingConfigurationException(
                        "Missing Shipping Fee bussiness configuration", KEY_SHIPPING_FEE));
    final String json = shippingFeeBusinessConfig.getJsonValue();
    shippingFeeConfiguration =
        ObjectMapperUtils.readValue(json, ShippingFeeConfiguration.class)
            .orElseThrow(
                () ->
                    new WrongConfigurationException(
                        "Wrong business configuration, the schema must match with classpath '%s'"
                            .formatted(ShippingFeeConfiguration.class.getName()),
                        KEY_SHIPPING_FEE));
  }

  @Override
  public void calculate(@NonNull Order order) {
    BigDecimal finalAmount = order.getFinalAmount();
    final String shippingAddrCode = order.getShippingAddress().getCode();
    final String customerCode = order.getCustomer().getCode();

    Long provinceId = null;
    final Optional<AddressDetail> shippingAddrDetailOpt =
        addrDetailDao.selectByCodeAndCustomerCode(shippingAddrCode, customerCode);
    if (shippingAddrDetailOpt.isPresent()) {
      provinceId = shippingAddrDetailOpt.get().getProvinceId();
    } else {
      final WardDetail wardDetail =
          wardDetailDao
              .selectById(order.getShippingAddress().getWard().getId())
              .orElseThrow(() -> new ConflictException("Can't identify shipping address"));
      provinceId = wardDetail.getProvinceId();
    }
    Optional.of(provinceId)
        .ifPresent(
            pId -> {
              final BigDecimal shippingFeeAmount =
                  shippingFeeConfiguration.shippingFees().stream()
                      .filter(sf -> sf.provinceIds().contains(pId))
                      .map(sf -> sf.unitPrice())
                      .findFirst()
                      .orElse(shippingFeeConfiguration.defaultFee());
              order.setShippingFeeAmount(shippingFeeAmount);
            });
    finalAmount = finalAmount.add(order.getShippingFeeAmount());
    order.setFinalAmount(finalAmount);
  }
}
