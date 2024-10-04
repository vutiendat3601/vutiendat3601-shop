import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from './cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  constructor() {}

  addToCart(newCartItem: CartItem) {
    const cartItem = this.cartItems.find((cI) => cI.productNo === newCartItem.productNo);
    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cartItems.push(newCartItem);
    }
    this.computeCartTotals();
  }

  decrementQuantity(decrementCartItem: CartItem) {
    const cartItem = this.cartItems.find(
      (cI) => cI.productNo === decrementCartItem.productNo
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
      (cI) => cI.productNo === removeCartItem.productNo
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
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  getCartItems() {
    return this.cartItems;
  }
}
