import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../domain/cart/cart.service';
import { ProductService } from '../../domain/product/product.service';
import { ProductDto } from '../../domain/product/product-dto';
import { CartItem } from '../../domain/cart/cart-item';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.scss',
})
export class ProductDetailsComponent {
  productDto: ProductDto | null = null;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleProductDetails();
    });
  }

  handleProductDetails() {
    const theProductNo: string = this.route.snapshot.paramMap.get('productNo')!;
    this.productService
      .getProductByProductNo(theProductNo)
      .subscribe((data) => {
        this.productDto = data;
      });
  }

  addToCart(productDto: ProductDto | null) {
    if (productDto) {
      const cartItem = new CartItem(
        productDto.name,
        productDto.productNo,
        productDto.thumbnail,
        productDto.unitPrice,
        1,
        productDto.categoryCode,
        null,
        null,
        null
      );
      this.cartService.addToCart(cartItem);
    }
  }
}
