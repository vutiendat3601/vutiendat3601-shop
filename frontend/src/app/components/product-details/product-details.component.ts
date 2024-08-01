import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from '../../models/cart-item';
import { Product } from '../../models/product';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent implements OnInit {
  product: Product = new Product();

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
    const theProductId: number = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProduct(theProductId).subscribe((data) => {
      this.product = data;
    });
  }

  addToCart(product: Product) {
    const cartItem = new CartItem(
      product.id!,
      product.name!,
      product.imageUrl!,
      product.unitPrice!
    );
    this.cartService.addToCart(cartItem);
  }
}
