import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from './cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];
  cartItemsChanged: Subject<CartItem[]> = new BehaviorSubject<CartItem[]>([]);
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  constructor() {}

  addToCart(newCartItem: CartItem) {
    console.log('this.cartItems be', this.cartItems);
    const cartItem = this.cartItems.find(
      (cI) => cI.productNo === newCartItem.productNo
    );
    console.log('add', cartItem);

    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cartItems = [...this.cartItems, newCartItem];
    }
    console.log('this.cartItems', this.cartItems);
    this.computeCartTotals();
    this.cartItemsChanged.next(this.cartItems);
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
    this.cartItemsChanged.next(this.cartItems);
    console.log(this.cartItems);
  }

  remove(removeCartItem: CartItem) {
    const cartItemIndex = this.cartItems.findIndex(
      (cI) => cI.productNo === removeCartItem.productNo
    );
    if (cartItemIndex > -1) {
      this.cartItems.splice(cartItemIndex, 1);
      this.computeCartTotals();
    }
    this.cartItemsChanged.next(this.cartItems);
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
}
