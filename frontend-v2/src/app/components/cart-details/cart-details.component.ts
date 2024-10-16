import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faMinus, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { AddressService } from '../../domain/address/address.service';
import { DistrictDto } from '../../domain/address/district-dto';
import { ProvinceDto } from '../../domain/address/province-dto';
import { WardDto } from '../../domain/address/ward-dto';
import { CartService } from '../../domain/cart/cart.service';
import { CouponDto } from '../../domain/coupon/coupon-dto';
import { CreateOrderPreviewRequest } from '../../domain/order/create-order-preview-request';
import { CreateOrderRequest } from '../../domain/order/create-order-request';
import { OrderDto } from '../../domain/order/order-dto';
import { OrderService } from '../../domain/order/order.service';
import { CategoryService } from '../../domain/product/category.service';
import { ProductService } from '../../domain/product/product.service';
import { CreateAddressRequest } from './../../domain/address/create-address-request';
import { CartItem } from './../../domain/cart/cart-item';
import { CouponService } from './../../domain/coupon/coupon.service';
import { CreateOrderItemDto } from './../../domain/order/create-order-item-dto';
import { CreateOrderPaymentRequest } from '../../domain/order/create-order-payment-request';

@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [
    RouterLink,
    CurrencyPipe,
    FontAwesomeModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
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
  // shippingFee = 0;
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
  shippingFeeCouponCodes: string[] = [];

  // Address
  ADDRESS_COMPARATOR = (a: { name: string }, b: { name: string }) =>
    a.name.localeCompare(b.name);

  addrFormGroup = new FormGroup({
    provinceId: new FormControl<number | null>(null),
    districtId: new FormControl<number | null>(null),
    wardId: new FormControl<number | null>(null),
    street: new FormControl<string | null>(null),
  });
  provinces: ProvinceDto[] = [];
  districts: DistrictDto[] = [];
  wards: WardDto[] = [];
  selectedWardId: number | null = null;

  constructor(
    private cartService: CartService,
    private categoryService: CategoryService,
    private productService: ProductService,
    private addressService: AddressService,
    private orderService: OrderService,
    private couponService: CouponService
  ) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.listCartDetails();
    this.listProvinces();
    this.listShippingFeeCoupons();
  }

  listShippingFeeCoupons() {
    this.couponService
      .getAvailableShippingFeeCoupons()
      .subscribe((couponDtoPage) => {
        this.shippingFeeCouponCodes = couponDtoPage.items.map(
          (couponDto: CouponDto) => couponDto.code
        );
      });
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
      this.getOrderPreview();
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
    // this.onCouponSelected(cartItem);
  }

  decrementQuantity(cartItem: CartItem) {
    this.cartService.decrementQuantity(cartItem);
    // this.onCouponSelected(cartItem);
  }

  remove(cartItem: CartItem) {
    this.cartService.remove(cartItem);
    // this.onCouponSelected(cartItem);
  }

  handleCartItemCouponSelected(cartItem: CartItem) {
    this.cartService.updateCartItem(cartItem.productNo, cartItem);
  }

  listProvinces(): void {
    this.addressService.getProvinces().subscribe((provinceDtoPage) => {
      this.provinces = provinceDtoPage.items;
      this.provinces.sort(this.ADDRESS_COMPARATOR);
    });
  }

  handleProvinceIdSelected(event: Event): void {
    const selectedProvinceId = parseInt(
      (event.target as HTMLSelectElement).value
    );
    this.wards = [];
    this.addrFormGroup.patchValue({ districtId: null, wardId: null });
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

  handleDistrictIdSelected(event: Event): void {
    const selectedWardId = parseInt((event.target as HTMLSelectElement).value);
    if (selectedWardId > -1) {
      this.addressService.getWards(selectedWardId).subscribe((wardDtoPage) => {
        this.wards = wardDtoPage.items;
        this.wards.sort(this.ADDRESS_COMPARATOR);
      });
      return;
    }
  }

  handleWardIdSelected(event: Event) {
    this.selectedWardId = parseInt((event.target as HTMLSelectElement).value);
  }

  getOrderPreview() {
    const orderItemDtos: CreateOrderItemDto[] = this.cartItems.map(
      (ci) => new CreateOrderItemDto(ci.productNo, ci.quantity, ci.couponCode)
    );
    this.orderService
      .getOrderPreview(
        new CreateOrderPreviewRequest(this.selectedWardId, null, orderItemDtos)
      )
      .subscribe((orderPreview) => {
        this.orderPreview = orderPreview;
        this.orderPreview.items.forEach((item) => {
          const ci = this.cartItems.find(
            (ci) => ci.productNo === item.productNo
          );
          if (ci) {
            ci.finalAmount = item.finalAmount;
            // ci.couponCode = item.couponCode;
            ci.productNo = item.productNo;
            ci.quantity = item.quantity;
            ci.couponAmount = item.couponAmount;
          }
        });
      });
  }

  checkout() {
    const street = this.addrFormGroup.get('street')?.value;
    const wardId = this.selectedWardId;
    if (street && wardId) {
      this.addressService
        .createAddress(new CreateAddressRequest(street, wardId))
        .subscribe((addrDetail) => {
          const orderItemDtos: CreateOrderItemDto[] = this.cartItems.map(
            (ci) =>
              new CreateOrderItemDto(ci.productNo, ci.quantity, ci.couponCode)
          );
          const createOrderReq: CreateOrderRequest = new CreateOrderRequest(
            addrDetail.code,
            null,
            orderItemDtos
          );
          this.orderService
            .createOrder(createOrderReq)
            .subscribe((orderDto) => {
              console.log('orderDto', orderDto);
              this.orderService
                .createOrderPayment(
                  orderDto.trackingNumber,
                  new CreateOrderPaymentRequest('VN_PAY')
                )
                .subscribe((orderPaymentDto) => {
                  window.location.href = orderPaymentDto.paymentUrl;
                });
            });
        });
    }
  }
}
