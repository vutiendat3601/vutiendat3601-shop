import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from './cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private readonly cartItems: CartItem[] = [];
  private readonly totalProductAmount: Subject<number> =
    new BehaviorSubject<number>(0);
  private readonly numberOfProducts: Subject<number> =
    new BehaviorSubject<number>(0);
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
    let totalProductAmount: number = 0;

    for (const cartItem of this.cartItems) {
      totalProductAmount += cartItem.quantity * cartItem.unitPrice;
    }

    // publish the new values ... all subscribers will receive the new data
    this.totalProductAmount.next(totalProductAmount);
    this.numberOfProducts.next(this.cartItems.length);
  }

  totalProductAmountChanged(): Subject<number> {
    return this.totalProductAmount;
  }
  numberOfProductsChanged(): Subject<number> {
    return this.numberOfProducts;
  }
}
