import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from '../../models/cart-item';
import { Product } from '../../models/product';
import { BannerService } from '../../services/banner.service';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { Banner } from '../../models/banner';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number = 1;
  searchMode: boolean = false;
  keyword: string = '';
  previousKeyword: string = '';

  banners: Banner[] = [];

  // Phân trang
  page: number = 1;
  size: number = 10;
  totalElements: number = 0;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private bannerService: BannerService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
    this.bannerService.getBanners().subscribe((data) => {
      this.banners = data;
    });
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode) {
      this.handleSearchProducts();
    } else {
      this.handleListProducts();
    }
  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    if (this.previousKeyword != theKeyword) {
      this.page = 1;
    }
    this.previousKeyword = theKeyword;
    this.productService
      .searchProductsPaginate(this.page - 1, this.size, theKeyword)
      .subscribe(this.processResult());

    this.bannerService.getBanners().subscribe((data) => {
      this.banners = data;
    });
  }

  handleListProducts() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if (hasCategoryId) {
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    } else {
      this.currentCategoryId = 1;
    }
    if (this.previousCategoryId != this.currentCategoryId) {
      this.page = 1;
    }
    this.previousCategoryId = this.currentCategoryId;

    this.productService
      .getProductListPaginate(this.page - 1, this.size, this.currentCategoryId)
      .subscribe(this.processResult());

    this.bannerService.getBanners().subscribe((data) => {
      this.banners = data;
    });
  }

  updatePageSize(size: string) {
    this.size = Number.parseInt(size);
    this.page = 1;
    this.listProducts();
  }

  processResult() {
    return (data: any) => {
      this.products = data._embedded.products;
      this.page = data.page.number + 1;
      this.size = data.page.size;
      this.totalElements = data.page.totalElements;
    };
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
