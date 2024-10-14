import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../domain/cart/cart-item';
import { CartService } from '../../domain/cart/cart.service';
import { RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { faMinus, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderDto } from '../../domain/order/order-dto';
import { CategoryService } from '../../domain/product/category.service';
import { CouponDto } from '../../domain/coupon/coupon-dto';
import { CouponService } from '../../domain/coupon/coupon.service';
import { CreateOrderRequest } from '../../domain/order/create-order-request';
import { ProductService } from '../../domain/product/product.service';
import { OrderService } from '../../domain/order/order.service';
import { CreateOrderItemDto } from '../../domain/order/create-order-item-dto';
import { ProvinceDto } from '../../domain/address/province-dto';
import { DistrictDto } from '../../domain/address/district-dto';
import { WardDto } from '../../domain/address/ward-dto';
import { AddressService } from '../../domain/address/address.service';

@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [
    RouterLink,
    CurrencyPipe,
    FontAwesomeModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule
  ],
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
  shippingFee = 0;
  orderPreview: OrderDto | null = null;
  createOrderReq: CreateOrderRequest | null = null;

  // Product coupons and Category coupons
  selectedCoupon: string = '';
  productCoupons: Map<string, string[]> = new Map<string, string[]>();
  categoryCoupons: Map<string, CouponDto[]> = new Map<string, CouponDto[]>();

  // Shipping fee coupons
  selectedShippingFeeCoupon: string = '';
  availableCoupons = [
    { code: 'FREESHIP', description: 'Miễn phí vận chuyển' },
    { code: 'DISCOUNT10', description: 'Giảm 10%' },
    { code: 'SALE2024', description: 'Giảm giá mùa sale 2024' },
  ];
  filteredCoupons = [...this.availableCoupons];

  // Address
  ADDRESS_COMPARATOR = (a: { name: string }, b: { name: string }) =>
    a.name.localeCompare(b.name);

  addrFormGroup = new FormGroup({
    provinceId: new FormControl<number>(-1),
    districtId: new FormControl<number>(-1),
    ward: new FormControl<number>(-1),
  });
  provinces: ProvinceDto[] = [];
  districts: DistrictDto[] = [];
  wards: WardDto[] = [];
  selectedWardId = -1;

  constructor(
    private cartService: CartService,
    private categoryService: CategoryService,
    private productService: ProductService,
    private addressService: AddressService,
    private orderService: OrderService,
  ) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.listCartDetails();
    this.listProvinces();
  }

  listCartDetails() {
    this.cartService.totalPrice.subscribe((data) => (this.totalPrice = data));
    this.cartService.totalQuantity.subscribe(
      (data) => (this.totalQuantity = data)
    );
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
      this.cartItems.forEach((cI) => {
        if (cI.categoryCode) {
          this.categoryService
            .getCoupons(cI.categoryCode)
            .subscribe((categoryCouponPage) =>
              this.categoryCoupons.set(
                cI.categoryCode,
                categoryCouponPage.items
              )
            );
        }
        if (cI.productNo) {
          this.productService
            .getCouponsByProductNo(cI.productNo)
            .subscribe((productCouponPage) => {
              const couponCodes = productCouponPage.items.map(
                (item: any) => item.code
              );
              this.productCoupons.set(cI.productNo, couponCodes);
            });
        }
      });
    });
  }

  getCartItemCoupons(productNo: string, categoryCode: string): string[] {
    let coupons: string[] = [];
    if (this.productCoupons.has(productNo)) {
      coupons = [...this.productCoupons.get(productNo)!];
    }
    if (categoryCode && this.categoryCoupons.has(categoryCode)) {
      const categoryCoupons = this.categoryCoupons.get(categoryCode)!;
      const categoryCouponCodes = categoryCoupons.map((coupon) => coupon.code);
      coupons = [...coupons, ...categoryCouponCodes];
    }
    return coupons;
  }

  incrementQuantity(cartItem: CartItem) {
    this.cartService.addToCart(cartItem);
    this.onSelectedChange(cartItem);
  }

  decrementQuantity(cartItem: CartItem) {
    this.cartService.decrementQuantity(cartItem);
    this.onSelectedChange(cartItem);
  }

  remove(cartItem: CartItem) {
    this.cartService.remove(cartItem);
    this.onSelectedChange(cartItem);
  }

  handleWardSelected(event: Event) {
    this.selectedWardId = parseInt((event.target as HTMLSelectElement).value);
    if (this.selectedWardId > -1) {
      this.cartItems.forEach(cartItem => this.onSelectedChange(cartItem));
    }
  }

  onSelectedChange(cartItem: CartItem) {
    this.orderService
      .getOrderPreview(
        new CreateOrderRequest(this.selectedWardId, null, [
          new CreateOrderItemDto(
            cartItem.productNo,
            cartItem.quantity,
            cartItem.coupon
          ),
        ])
      )
      .subscribe((data: any) => {
        cartItem.finalPrice = data.items[0].finalAmount;
        this.shippingFee = data.shippingFeeAmount;
        this.cartService.computeCartTotals();
      });
  }

  listProvinces(): void {
    this.addressService.getProvinces().subscribe((provinceDtoPage) => {
      this.provinces = provinceDtoPage.items;
      this.provinces.sort(this.ADDRESS_COMPARATOR);
    });
  }

  handleProvinceSelected(event: Event): void {
    const selectedProvinceId = parseInt(
      (event.target as HTMLSelectElement).value
    );
    this.wards = [];
    this.addrFormGroup.patchValue({ districtId: -1, ward: -1 });
    if (selectedProvinceId > -1) {
      this.addressService
        .getDistricts(selectedProvinceId)
        .subscribe((districtDtoPage) => {
          this.districts = districtDtoPage.items;
          this.districts.sort(this.ADDRESS_COMPARATOR);
        });
      return;
    }
    this.districts = [];
  }

  handleDistrictSelected(event: Event): void {
    const selectedWardId = parseInt((event.target as HTMLSelectElement).value);
    if (selectedWardId > -1) {
      this.addressService.getWards(selectedWardId).subscribe((wardDtoPage) => {
        this.wards = wardDtoPage.items;
        this.wards.sort(this.ADDRESS_COMPARATOR);
      });
      return;
    }
  }
}
