package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.address.Address;
import vn.io.vutiendat3601.shop.v2.address.AddressDao;
import vn.io.vutiendat3601.shop.v2.address.Ward;
import vn.io.vutiendat3601.shop.v2.address.WardDao;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.common.PageDto;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.customer.CustomerDao;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
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

    final Order order = Order.builder().customer(customer).build();
    if (Objects.nonNull(orderPreviewReq.wardId())) {
      final Ward ward =
          wardDao
              .selectById(orderPreviewReq.wardId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Ward not found: (id=%s)".formatted(orderPreviewReq.wardId())));
      Address shippingAddr = Address.builder().ward(ward).build();
      order.setShippingAddress(shippingAddr);
    }
    calculateOrderItemAmount(order, orderPreviewReq.items());

    // Apply Shipping Fee
    if (Objects.nonNull(order.getShippingAddress())) {
      shippingFeeCalculator.calculate(order);
    }

    // Apply VAT
    vatCaclator.calculate(order);
    return orderDtoMapper.apply(order);
  }

  public OrderDto getOrderByCurrentUser(@NonNull String trackingNumber) {
    final String customerCode = authContext.getUser().customerCode();
    final Order order =
        orderDao
            .selectByTrackingNumberAndCustomerCode(trackingNumber, customerCode)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Order not found: (trackingNumber=%s)".formatted(trackingNumber)));
    return orderDtoMapper.apply(order);
  }

  public PageDto<OrderDto> getOrders(int page, int size) {
    page--;
    final Page<Order> orderPage = orderDao.selectAllByOrderByCreatedAtDesc(page, size);
    return PageDto.of(orderPage).map(orderDtoMapper);
  }

  public PageDto<OrderDto> getOrdersByCurrentUser(int page, int size) {
    page--;
    final String customerCode = authContext.getUser().customerCode();
    final Page<Order> orderPage = orderDao.selectAllByCustomerCode(customerCode, page, size);
    return PageDto.of(orderPage).map(orderDtoMapper);
  }

  public OrderDto createOrder(@NonNull CreateOrderRequest createOrderReq) {
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
    return orderDtoMapper.apply(order);
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

  public void updateOrderStatus(
      @NonNull String trackingNumber, @NonNull UpdateOrderStatusRequest updateOrderStatusReq) {
    final Order order =
        orderDao
            .selectByTrackingNumber(trackingNumber)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Order not found: (trackingNumber=%s)".formatted(trackingNumber)));
    final OrderStatus status = order.getStatus();
    switch (updateOrderStatusReq.status()) {
      case DELIVERING:
        if (!OrderStatus.PAID.equals(status)) {
          throw new ConflictException(
              "Current order status must be 'PAID' status to change to 'DELIVERING'");
        }
        order.setStatus(OrderStatus.DELIVERING);
        break;
      case DELIVERED:
        if (!OrderStatus.DELIVERING.equals(status)) {
          throw new ConflictException(
              "Current order status must be 'PAID' status to change to 'DELIVERING'");
        }
        order.setStatus(OrderStatus.DELIVERED);
        break;
      default:
        break;
    }
    orderDao.update(order);
  }
}
