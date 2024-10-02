import { CurrencyPipe } from '@angular/common';
import { Component } from '@angular/core';
import { CartService } from '../../cart/cart.service';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
@Component({
  selector: 'app-cart-status',
  standalone: true,
  imports: [FontAwesomeModule, CurrencyPipe],
  templateUrl: './cart-status.component.html',
  styleUrl: './cart-status.component.scss',
})
export class CartStatusComponent {
  faShoppingCart = faShoppingCart;
  totalProductAmount: number = 0;
  numberOfProducts: number = 0;

  public constructor(private readonly cartService: CartService) {}

  ngOnInit(): void {
    this.cartService
      .numberOfProductsChanged()
      .subscribe(
        (numberOfProducts) => (this.numberOfProducts = numberOfProducts)
      );
    this.cartService
      .totalProductAmountChanged()
      .subscribe(
        (totalProductAmount) => (this.totalProductAmount = totalProductAmount)
      );
  }

  goToCartDetail(): void {
    alert('Go to cart detail');
  }
}
