import { Injectable } from '@angular/core';
import { CartItem } from './cart-item';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];
  totalProductAmount: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);
  constructor() {}

  addToCart(newCartItem: CartItem) {
    const cartItem = this.cartItems.find(
      (ci) => ci.productNo === newCartItem.productNo
    );
    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cartItems.push(newCartItem);
    }
    this.computeCartTotals();
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (const cartItem of this.cartItems) {
      totalPriceValue += cartItem.quantity * cartItem.unitPrice;
      totalQuantityValue += cartItem.quantity;
    }

    // publish the new values ... all subscribers will receive the new data
    this.totalProductAmount.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }
}
