import { CurrencyPipe } from '@angular/common';
import { Component } from '@angular/core';
import { CartService } from '../../cart/cart.service';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-cart-status',
  standalone: true,
  imports: [FontAwesomeModule, CurrencyPipe, RouterLink],
  templateUrl: './cart-status.component.html',
  styleUrl: './cart-status.component.scss',
})
export class CartStatusComponent {
  faShoppingCart = faShoppingCart;
  totalProductAmount: number = 0;
  numberOfProducts: number = 0;

  public constructor(private readonly cartService: CartService) {}

  ngOnInit(): void {
    this.updateCartStatus();
  }

  updateCartStatus() {
    this.cartService.totalPrice.subscribe((totalPrice) => {
      this.totalProductAmount = totalPrice;
    });
    this.cartService.totalQuantity.subscribe((totalQuantity) => {
      this.numberOfProducts = totalQuantity;
    });
  }
}
