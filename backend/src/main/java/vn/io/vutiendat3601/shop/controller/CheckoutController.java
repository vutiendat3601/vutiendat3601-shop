package vn.io.vutiendat3601.shop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.vutiendat3601.shop.dto.Purchase;
import vn.io.vutiendat3601.shop.dto.PurchaseResponse;
import vn.io.vutiendat3601.shop.service.CheckoutService;

@CrossOrigin
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

  private CheckoutService checkoutService;

  public CheckoutController(CheckoutService checkoutService) {
    this.checkoutService = checkoutService;
  }

  @PostMapping("/purchase")
  public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

    PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

    return purchaseResponse;
  }
}
