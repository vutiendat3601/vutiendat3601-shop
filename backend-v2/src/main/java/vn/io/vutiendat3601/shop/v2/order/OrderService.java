package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.address.Address;
import vn.io.vutiendat3601.shop.v2.address.AddressDao;
import vn.io.vutiendat3601.shop.v2.address.Ward;
import vn.io.vutiendat3601.shop.v2.address.WardDao;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.customer.CustomerDao;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.fee.ProductCouponApplier;
import vn.io.vutiendat3601.shop.v2.fee.ShippingFeeCalculator;
import vn.io.vutiendat3601.shop.v2.fee.VatCaclator;
import vn.io.vutiendat3601.shop.v2.product.Product;
import vn.io.vutiendat3601.shop.v2.product.ProductDao;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
  private final AuthContext authContext;

  private final AddressDao addrDao;

  private final OrderDao orderDao;

  private final CustomerDao customerDao;

  private final ProductDao productDao;

  private final ProductCouponApplier productCouponApplier;

  private final ShippingFeeCalculator shippingFeeCalculator;

  private final VatCaclator vatCaclator;

  private final OrderDtoMapper orderDtoMapper;

  private final WardDao wardDao;

  public OrderDto getOrderPreview(@NonNull OrderPreviewRequest orderPreviewReq) {
    final String customerCode = authContext.getUser().customerCode();
    final Customer customer =
        customerDao
            .selectByCode(customerCode)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

    final Ward ward =
        wardDao
            .selectById(orderPreviewReq.wardId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Ward not found: (id=%s)".formatted(orderPreviewReq.wardId())));
    Address shippingAddr = Address.builder().ward(ward).build();
    final Order order = Order.builder().customer(customer).shippingAddress(shippingAddr).build();
    calculateOrderItemAmount(order, orderPreviewReq.items());

    // Apply Shipping Fee
    shippingFeeCalculator.calculate(order);

    // Apply VAT
    vatCaclator.calculate(order);
    return orderDtoMapper.apply(order);
  }

  public CreatedOrderDto createOrder(@NonNull CreateOrderRequest createOrderReq) {
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
    Order order = Order.builder().customer(customer).shippingAddress(shippingAddr).build();
    calculateOrderItemAmount(order, createOrderReq.items());

    // Apply Shipping Fee
    shippingFeeCalculator.calculate(order);

    // Apply VAT
    vatCaclator.calculate(order);
    long orderId = orderDao.insert(order);
    order =
        orderDao.selectById(orderId).orElseThrow(() -> new RuntimeException("Can't create order"));
    return new CreatedOrderDto(order.getTrackingNumber());
  }

  private void calculateOrderItemAmount(
      @NonNull Order order, @NonNull List<CreateOrderItemDto> items) {
    for (CreateOrderItemDto itemDto : items) {
      final Product product =
          productDao
              .selectByProductNoAndIsActiveTrue(itemDto.productNo())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Product not found: (id=%s)".formatted(itemDto.productNo())));
      final BigDecimal unitPrice = product.getUnitPrice();
      final int quantity = itemDto.quantity();

      final BigDecimal itemFinalAmount = unitPrice.multiply(new BigDecimal(quantity));

      final OrderItem item =
          OrderItem.builder()
              .totalAmount(itemFinalAmount)
              .finalAmount(itemFinalAmount)
              .quantity(itemDto.quantity())
              .product(product)
              .order(order)
              .build();

      // Apply Product coupon
      final String couponCode = itemDto.couponCode();
      if (Objects.nonNull(couponCode)) {
        productCouponApplier.apply(couponCode, item);
      }
      order.addItem(item);
    }
  }
}
