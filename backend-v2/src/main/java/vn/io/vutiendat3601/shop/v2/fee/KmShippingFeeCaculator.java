package vn.io.vutiendat3601.shop.v2.fee;

import static vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationValueType.JSON;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConstant.KEY_SHIPPING_FEE;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.address.AddressDetail;
import vn.io.vutiendat3601.shop.v2.address.AddressDetailDao;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfiguration;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationDao;
import vn.io.vutiendat3601.shop.v2.exception.MissingConfigurationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.exception.WrongConfigurationException;
import vn.io.vutiendat3601.shop.v2.order.Order;
import vn.io.vutiendat3601.shop.v2.util.ObjectMapperUtils;

@RequiredArgsConstructor
@Service
public class KmShippingFeeCaculator implements ShippingFeeCalculator {
  @Setter private ShippingFeeConfiguration shippingFeeConfiguration;
  private final BusinessConfigurationDao businessConfigDao;
  private final AddressDetailDao addrDetailDao;

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
    final AddressDetail shippingAddrDetail =
        addrDetailDao
            .selectByCodeAndCustomerCode(shippingAddrCode, customerCode)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Addres not found: (code=%s)".formatted(shippingAddrCode)));
    final BigDecimal shippingFeeAmount =
        shippingFeeConfiguration.shippingFees().stream()
            .filter(sf -> sf.provinceIds().contains(shippingAddrDetail.getProvinceId()))
            .map(sf -> sf.unitPrice())
            .findFirst()
            .orElse(shippingFeeConfiguration.defaultFee());
    order.setShippingFeeAmount(shippingFeeAmount);
    finalAmount = finalAmount.add(shippingFeeAmount);
    order.setFinalAmount(finalAmount);
  }
}
