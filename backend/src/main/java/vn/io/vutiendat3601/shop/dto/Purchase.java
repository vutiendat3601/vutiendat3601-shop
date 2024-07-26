package vn.io.vutiendat3601.shop.dto;

import java.util.Set;
import lombok.Data;
import vn.io.vutiendat3601.shop.entity.Address;
import vn.io.vutiendat3601.shop.entity.Customer;
import vn.io.vutiendat3601.shop.entity.Order;
import vn.io.vutiendat3601.shop.entity.OrderItem;

@Data
public class Purchase {
  private Customer customer;

  private Address shippingAddress;

  private Address billingAddress;

  private Order order;

  private Set<OrderItem> orderItems;
}
