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
  styleUrl: './product-details.component.scss'
})
export class ProductDetailsComponent {
  productDto: ProductDto = new ProductDto();

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
    this.productService.getProductByProductNo(theProductNo).subscribe((data) => {
      this.productDto = data;
    });
  }

  addToCart(productDto: ProductDto) {
    const cartItem = new CartItem(
      productDto.id,
      productDto.name,
      productDto.product_no,
      productDto.thumbnail,
      productDto.unitPrice
    );
    this.cartService.addToCart(cartItem);
  }
}
