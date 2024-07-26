package vn.io.vutiendat3601.shop.service;

import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.dto.Purchase;
import vn.io.vutiendat3601.shop.dto.PurchaseResponse;
import vn.io.vutiendat3601.shop.entity.Customer;
import vn.io.vutiendat3601.shop.entity.Order;
import vn.io.vutiendat3601.shop.entity.OrderItem;
import vn.io.vutiendat3601.shop.repository.CustomerRepository;

@Service
public class CheckoutService {
  private CustomerRepository customerRepo;

  public CheckoutService(CustomerRepository customerRepo) {
    this.customerRepo = customerRepo;
  }

  @Transactional
  public PurchaseResponse placeOrder(Purchase purchase) {
    Order order = purchase.getOrder();
    String orderTrackingNumber = generateOrderTrackingNumber();
    order.setOrderTrackingNumber(orderTrackingNumber);
    Set<OrderItem> orderItems = purchase.getOrderItems();
    orderItems.forEach(item -> order.add(item));
    order.setBillingAddress(purchase.getBillingAddress());
    order.setShippingAddress(purchase.getShippingAddress());
    Customer customer = purchase.getCustomer();
    customer.add(order);
    customerRepo.save(customer);
    return new PurchaseResponse(orderTrackingNumber);
  }

  private String generateOrderTrackingNumber() {
    return UUID.randomUUID().toString();
  }
}
