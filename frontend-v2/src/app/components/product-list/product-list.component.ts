import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from '../../domain/cart/cart-item';
import { CartService } from '../../domain/cart/cart.service';
import { RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryMenuComponent } from '../category-menu/category-menu.component';
import { ProductService } from '../../domain/product/product.service';
import { PageDto } from '../../common/page-dto';
import { ProductDto } from '../../domain/product/product-dto';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CategoryMenuComponent,
    RouterLink,
    NgbPaginationModule,
    CurrencyPipe,
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductListComponent {
  productDtos: ProductDto[] = [];
  trendingProducts: ProductDto[] = [];
  currentCategoryCode?: string;
  previousCategoryCode?: string;
  searchMode: boolean = false;
  keyword: string = '';
  previousKeyword: string = '';
  selectedCategoryCode: string | undefined;

  page: number = 1;
  size: number = 10;
  totalItems: number = 0;
  isCategorySelected: boolean = false;

  constructor(
    private readonly productService: ProductService,
    private readonly cartService: CartService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.listTrendingProducts();
    this.route.queryParamMap.subscribe(() => {
      this.listProducts();
    });
  }

  listTrendingProducts() {
    this.productService
      .getTrendingProducts(this.page, this.size)
      .subscribe((productDtoPage: PageDto<ProductDto>) => {
        this.trendingProducts = productDtoPage.items;
        this.totalItems = productDtoPage.totalItems;
      });
  }

  listProducts() {
    const hasCategoryCode: boolean =
      this.route.snapshot.queryParamMap.has('categoryCode');
    if (hasCategoryCode) {
      this.isCategorySelected = true;
      this.currentCategoryCode =
        this.route.snapshot.queryParamMap.get('categoryCode')!;
      this.handleRenderProducts(this.currentCategoryCode);
    } else {
      this.isCategorySelected = false;
      this.handleRenderProducts();
    }
  }

  onCategorySelected(categoryCode: string) {
    this.selectedCategoryCode = categoryCode;
    this.page = 1;
    this.listProducts();
  }

  handleRenderProducts(categoryCode?: string) {
    if (categoryCode) {
      this.productService
        .getProductsByCategoryCode(categoryCode, this.page, this.size)
        .subscribe(this.processResult());
    } else {
      this.productService
        .getTrendingProducts(this.page, this.size)
        .subscribe(this.processResult());
    }
  }

  processResult() {
    return (productDtoPage: PageDto<ProductDto>) => {
      this.productDtos = productDtoPage.items;
      this.page = productDtoPage.page;
      this.size = productDtoPage.size;
      this.totalItems = productDtoPage.totalItems;
    };
  }

  updatePage(newPage: number) {
    this.page = +newPage;
    this.listProducts();
  }

  updatePageSize(newSize: string) {
    this.size = +newSize;
    this.page = 1;
    this.listProducts();
  }

  addToCart(productDto: ProductDto) {
    const cartItem = new CartItem(
      productDto.name,
      productDto.productNo,
      productDto.thumbnail,
      productDto.unitPrice,
      1,
      productDto.categoryCode,
      null,
      null,
      null,
      null
    );
    this.cartService.addToCart(cartItem);
  }

  calculateDiscountPercentage(
    unitPrice: number,
    unitListedPrice: number
  ): number {
    if (unitListedPrice > 0 && unitPrice < unitListedPrice) {
      return Math.round(
        ((unitListedPrice - unitPrice) / unitListedPrice) * 100
      );
    }
    return 0;
  }
}
