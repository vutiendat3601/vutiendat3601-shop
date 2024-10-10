import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../domain/cart/cart-item';
import { CartService } from '../../domain/cart/cart.service';
import { RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { faMinus, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, FontAwesomeModule, FormsModule, CommonModule],
  templateUrl: './cart-details.component.html',
  styleUrl: './cart-details.component.scss',
})
export class CartDetailsComponent implements OnInit {
  faMinus = faMinus;
  faPlus = faPlus;
  faTrash = faTrash;

  totalPrice: number = 0;
  totalQuantity: number = 0;
  cartItems: CartItem[] = [];
  defaultShippingFee = 20000;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.listCartDetails();
  }

  updateCartStatus() {
    this.cartService.totalPrice.subscribe(
      (totalPrice) => (this.totalPrice = totalPrice)
    );
    this.cartService.totalQuantity.subscribe(
      (totalQuantity) => (this.totalQuantity = totalQuantity)
    );
    this.cartService.cartItemsChanged.subscribe((cartItems) => {
      this.cartItems = cartItems;
      console.log(this.cartItems);
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

  selectedCoupon: string = '';
  availableCoupons = [
    { code: 'FREESHIP', description: 'Miễn phí vận chuyển' },
    { code: 'DISCOUNT10', description: 'Giảm 10%' },
    { code: 'SALE2024', description: 'Giảm giá mùa sale 2024' }
  ];

  filteredCoupons = [...this.availableCoupons];

  filterCoupons() {
    // Lọc các mã giảm giá dựa trên từ khóa mà người dùng nhập
    this.filteredCoupons = this.availableCoupons.filter(coupon =>
      coupon.code.toLowerCase().includes(this.selectedCoupon.toLowerCase())
    );
  }

  applyCoupon() {
    // Kiểm tra và áp dụng mã giảm giá (nếu có)
    const selected = this.availableCoupons.find(coupon => coupon.code === this.selectedCoupon);
    if (selected) {
      // Thực hiện logic áp dụng mã giảm giá (ví dụ: giảm giá)
      console.log('Mã giảm giá được áp dụng:', selected.code);
    } else {
      console.log('Mã giảm giá không hợp lệ');
    }
  }
}
