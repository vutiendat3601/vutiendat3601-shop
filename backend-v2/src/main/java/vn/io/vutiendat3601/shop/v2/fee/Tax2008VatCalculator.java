package vn.io.vutiendat3601.shop.v2.fee;

import static vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationValueType.NUMBER;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConstant.KEY_VAT_FEE;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationDao;
import vn.io.vutiendat3601.shop.v2.exception.MissingConfigurationException;
import vn.io.vutiendat3601.shop.v2.order.Order;

@RequiredArgsConstructor
@Service
public class Tax2008VatCalculator implements VatCaclator {
  @Setter private BigDecimal vatFeeRatio;
  private final BusinessConfigurationDao businessConfigDao;

  @PostConstruct
  private void loadVatFeeConfig() {
    vatFeeRatio =
        businessConfigDao
            .selectByKeyAndValueType(KEY_VAT_FEE, NUMBER)
            .orElseThrow(
                () ->
                    new MissingConfigurationException(
                        "Missing VAT bussiness configuration", KEY_VAT_FEE))
            .getNumberValue();
  }

  @Override
  public void calculate(@NonNull Order order) {
    BigDecimal finalAmount = order.getFinalAmount();
    final BigDecimal vatFee = finalAmount.multiply(vatFeeRatio);
    order.setVatFeeAmount(vatFee);
    finalAmount = finalAmount.add(vatFee);
    order.setFinalAmount(finalAmount);
  }
}
