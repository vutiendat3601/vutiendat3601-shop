import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../cart/cart-item';
import { CartService } from '../../cart/cart.service';
import { RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { faMinus, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, FontAwesomeModule],
  templateUrl: './cart-details.component.html',
  styleUrl: './cart-details.component.scss'
})
export class CartDetailsComponent implements OnInit {
  faMinus = faMinus;
  faPlus = faPlus;
  faTrash = faTrash;
  totalPrice: number = 0;
  totalQuantity: number = 0;
  cartItems: CartItem[] = []

  // [
  //   {
  //     productNo: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     productThumbnail:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000,
  //     quantity: 1,
  //     couponCode: null
  //   },
  //   {
  //     productNo: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     productThumbnail:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000,
  //     quantity: 1,
  //     couponCode: null
  //   },
  //   {
  //     productNo: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     productThumbnail:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000,
  //     quantity: 1,
  //     couponCode: null
  //   },
  //   {
  //     productNo: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     productThumbnail:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000,
  //     quantity: 1,
  //     couponCode: null
  //   },
  //   {
  //     productNo: 'BOOK-TECH-1000',
  //     name: 'Crash Course in Pythonaa ne cac ban gi oi ta cung nhau',
  //     productThumbnail:
  //       'https://salt.tikicdn.com/cache/400x400/ts/category/2d/7c/45/e4976f3fa4061ab310c11d2a1b759e5b.png',
  //     unitPrice: 100000,
  //     quantity: 1,
  //     couponCode: null
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
