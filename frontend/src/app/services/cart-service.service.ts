import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from './../models/cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];

  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  constructor() {}

  addToCart(newCartItem: CartItem) {
    const cartItem = this.cartItems.find((cI) => cI.id === newCartItem.id);
    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cartItems.push(newCartItem);
    }
    this.computeCartTotals();
  }

  decrementQuantity(decrementCartItem: CartItem) {
    const cartItem = this.cartItems.find(
      (cI) => cI.id === decrementCartItem.id
    );
    if (cartItem) {
      cartItem.quantity--;
      if (cartItem.quantity === 0) {
        this.remove(decrementCartItem);
      } else {
        this.computeCartTotals();
      }
    }
  }

  remove(removeCartItem: CartItem) {
    const cartItemIndex = this.cartItems.findIndex(
      (cI) => cI.id === removeCartItem.id
    );
    if (cartItemIndex > -1) {
      this.cartItems.splice(cartItemIndex, 1);
      this.computeCartTotals();
    }
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let cartItem of this.cartItems) {
      totalPriceValue += cartItem.quantity * cartItem.unitPrice;
      totalQuantityValue += cartItem.quantity;
    }

    // publish the new values ... all subscribers will receive the new data
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  getCartItems() {
    return this.cartItems;
  }
}
