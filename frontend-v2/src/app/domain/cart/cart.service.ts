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

  constructor() {
    this.loadCartItemsFromLocalStorage();
  }

  public clearCart() {
    this.cartItems = [];
    this.cartItemsChanged.next(this.cartItems);
    this.computeCartTotals();
    this.saveCartItemsToLocalStorage();
  }

  loadCartItemsFromLocalStorage() {
    const cartItemsJson = localStorage.getItem('cartItems');
    if (cartItemsJson) {
      console.log(cartItemsJson);
      this.cartItems = JSON.parse(cartItemsJson);
      this.cartItemsChanged.next(this.cartItems);
      this.computeCartTotals();
    }
  }

  saveCartItemsToLocalStorage() {
    localStorage.setItem('cartItems', JSON.stringify(this.cartItems));
  }

  addToCart(newCartItem: CartItem) {
    const cartItem = this.cartItems.find(
      (cI) => cI.productNo === newCartItem.productNo
    );
    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cartItems = [...this.cartItems, newCartItem];
    }
    this.computeCartTotals();
    this.cartItemsChanged.next(this.cartItems);
    this.saveCartItemsToLocalStorage();
  }

  updateCartItem(productNo: string, updateCartItem: CartItem) {
    const cartItem = this.cartItems.find((ci) => ci.productNo === productNo);
    if (cartItem) {
      cartItem.couponCode = updateCartItem.couponCode;
      this.computeCartTotals();
      this.cartItemsChanged.next(this.cartItems);
      this.saveCartItemsToLocalStorage();
    }
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
    this.saveCartItemsToLocalStorage();
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
    this.saveCartItemsToLocalStorage();
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let cartItem of this.cartItems) {
      if (cartItem.couponCode && cartItem.finalAmount) {
        totalPriceValue += cartItem.finalAmount;
      } else {
        totalPriceValue += cartItem.quantity * cartItem.unitPrice;
      }
      totalQuantityValue += cartItem.quantity;
    }
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }
}
