package vn.io.vutiendat3601.shop.v2.order;

import static vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationValueType.JSON;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationValueType.NUMBER;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConstant.KEY_SHIPPING_FEE;
import static vn.io.vutiendat3601.shop.v2.business.BusinessConstant.KEY_VAT_FEE;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.address.Address;
import vn.io.vutiendat3601.shop.v2.address.AddressDao;
import vn.io.vutiendat3601.shop.v2.address.AddressDetail;
import vn.io.vutiendat3601.shop.v2.address.AddressDetailDao;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfiguration;
import vn.io.vutiendat3601.shop.v2.business.BusinessConfigurationDao;
import vn.io.vutiendat3601.shop.v2.coupon.CouponService;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.customer.CustomerDao;
import vn.io.vutiendat3601.shop.v2.exception.MissingConfigurationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.exception.WrongConfigurationException;
import vn.io.vutiendat3601.shop.v2.product.Product;
import vn.io.vutiendat3601.shop.v2.product.ProductDao;
import vn.io.vutiendat3601.shop.v2.shipping.ShippingFeeConfiguration;
import vn.io.vutiendat3601.shop.v2.util.ObjectMapperUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
  @Setter private BigDecimal vatFeeRatio;
  @Setter private ShippingFeeConfiguration shippingFeeConfiguration;

  private final AuthContext authContext;
  private final BusinessConfigurationDao businessConfigDao;
  private final AddressDao addrDao;
  private final OrderDao orderDao;
  private final CustomerDao customerDao;
  private final ProductDao productDao;
  private final CouponService couponService;
  private final AddressDetailDao addrDetailDao;

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

  public void createOrder(@NonNull CreateOrderRequest createOrderReq) {
    final String customerCode = authContext.getUser().customerCode();
    final Customer customer =
        customerDao
            .selectByCode(customerCode)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    final Address shippingAddr =
        addrDao
            .selectByCodeAndCustomerCode(createOrderReq.addressCode(), customerCode)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Address not found: (code=%s,customerCode=%s)"
                            .formatted(createOrderReq.addressCode(), customerCode)));
    final Order order = Order.builder().customer(customer).shippingAddress(shippingAddr).build();
    calculateOrderItemAmount(order, createOrderReq);
    calculateOrderAmount(order);
    orderDao.insert(order);
  }

  private void calculateOrderItemAmount(
      @NonNull Order order, @NonNull CreateOrderRequest createOrderReq) {
    for (CreateOrderItemDto itemDto : createOrderReq.items()) {
      final Product product =
          productDao
              .selectByProductNoAndIsActiveTrue(itemDto.productNo())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Product not found: (id=%s)".formatted(itemDto.productNo())));
      final String productNo = itemDto.productNo();
      final BigDecimal unitPrice = product.getUnitPrice();
      final int quantity = itemDto.quantity();
      final String couponCode = itemDto.couponCode();

      BigDecimal itemFinalAmount = unitPrice.multiply(new BigDecimal(quantity));

      // Apply coupon, only apply one item
      BigDecimal itemCouponAmount = new BigDecimal(0D);
      if (Objects.nonNull(couponCode)) {
        final BigDecimal appliedCouponUnitPrice =
            couponService.applyCouponForProductUnitPrice(couponCode, productNo);
        itemCouponAmount = unitPrice.subtract(appliedCouponUnitPrice);
        itemFinalAmount = itemFinalAmount.subtract(itemCouponAmount);
      }

      final OrderItem item =
          OrderItem.builder()
              .totalAmount(itemFinalAmount)
              .couponAmount(itemCouponAmount)
              .finalAmount(itemFinalAmount)
              .quantity(itemDto.quantity())
              .build();
      order.addItem(item);
    }
  }

  private void calculateOrderAmount(@NonNull Order order) {
    BigDecimal finalAmount = order.getTotalProductFinalAmount();
    // Apply Shipping Fee
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

    // Apply VAT
    final BigDecimal vatFeeAmount = finalAmount.multiply(vatFeeRatio);
    order.setVatFeeAmount(vatFeeAmount);
    finalAmount = finalAmount.add(vatFeeAmount);

    order.setFinalAmount(finalAmount);
  }
}
