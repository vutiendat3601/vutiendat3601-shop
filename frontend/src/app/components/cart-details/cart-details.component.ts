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
  
  // [
  //   {
  //     id: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000000,
  //     quantity: 1,
  //   },
  //   {
  //     id: 'BOOK-TECH-1001',
  //     name: 'Crash Course in Python',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 10,
  //     quantity: 1,
  //   },
  //   {
  //     id: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000000,
  //     quantity: 1,
  //   },
  //   {
  //     id: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000000,
  //     quantity: 1,
  //   },
  //   {
  //     id: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000000,
  //     quantity: 1,
  //   },
  //   {
  //     id: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     imageUrl:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000000,
  //     quantity: 1,
  //   },
  // ];

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
