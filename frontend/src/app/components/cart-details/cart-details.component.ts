import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrl: './cart-details.component.css',
})
export class CartDetailsComponent implements OnInit {
  totalPrice: number = 0;
  totalQuantity: number = 0;
  cartItems: CartItem[] = [];

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.listCartDetails();
  }

  updateCartStatus() {
    this.cartService.totalPrice.subscribe((totalPrice) => {
      this.totalPrice = totalPrice;
    });
    this.cartService.totalQuantity.subscribe((totalQuantity) => {
      this.totalQuantity = totalQuantity;
    });
  }
  listCartDetails() {
    this.cartItems = this.cartService.cartItems;

    this.cartService.totalPrice.subscribe((data) => (this.totalPrice = data));

    this.cartService.totalQuantity.subscribe(
      (data) => (this.totalQuantity = data)
    );
  }

  incrementQuantity(cartItem: CartItem) {
    this.cartService.addToCart(cartItem);
  }
  decrementQuantity(cartItem: CartItem) {
    this.cartService.decrementQuantity(cartItem);
  }
  remove(cartItem: CartItem) {
    this.cartService.remove(cartItem);
  }
}
