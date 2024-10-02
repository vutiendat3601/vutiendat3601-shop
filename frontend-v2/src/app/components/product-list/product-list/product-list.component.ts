import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from '../../../cart/cart-item';
import { CartService } from '../../../cart/cart.service';
import { RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryMenuComponent } from '../../category-menu/category-menu.component';
import { ProductService } from '../../../domain/product/product.service';
import { PageDto } from '../../../common/page-dto';
import { ProductDto } from '../../../domain/product/product-dto';

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
  currentCategoryCode?: string;
  previousCategoryCode?: string;
  searchMode: boolean = false;
  keyword: string = '';
  previousKeyword: string = '';

  // new properties for pagination
  page: number = 1;
  size: number = 10;
  totalItems: number = 0;

  constructor(
    private readonly productService: ProductService,
    private readonly cartService: CartService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.queryParamMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {
    // this.searchMode = this.route.snapshot.paramMap.has('keyword');
    // if (this.searchMode) {
    // this.handleSearchProducts();
    // } else {
    this.handleRenderProducts();
    // }
  }

  handleSearchProducts() {
    // const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    // if (this.previousKeyword != theKeyword) {
    //   this.page = 1;
    // }
    // this.previousKeyword = theKeyword;
    // this.productService
    //   .searchProductsPaginate(this.page - 1, this.size, theKeyword)
    //   .subscribe(this.processResult());
  }

  handleRenderProducts() {
    const hasCategoryCode: boolean =
      this.route.snapshot.queryParamMap.has('categoryCode');
    if (hasCategoryCode) {
      this.currentCategoryCode =
        this.route.snapshot.queryParamMap.get('categoryCode')!;
      this.productService
        .getProductsByCategoryCode(
          this.currentCategoryCode,
          this.page,
          this.size
        )
        .subscribe(this.processResult());
    } else {
      this.currentCategoryCode = undefined;
      this.productService
        .getTrendingProducts(this.page, this.size)
        .subscribe(this.processResult());
    }
    if (this.previousCategoryCode != this.currentCategoryCode) {
      this.page = 1;
    }
    this.previousCategoryCode = this.currentCategoryCode;
  }

  updatePageSize(size: string) {
    this.size = Number.parseInt(size);
    this.page = 1;
    this.listProducts();
  }

  processResult() {
    return (productDtoPage: PageDto<ProductDto>) => {
      this.productDtos = productDtoPage.items;
      this.page = productDtoPage.page;
      this.size = productDtoPage.size;
      this.totalItems = productDtoPage.totalItems;
    };
  }

  addToCart(productDto: ProductDto) {
    const cartItem = new CartItem(
      productDto.productNo,
      1,
      productDto.name,
      productDto.thumbnail,
      productDto.unitPrice
    );
    this.cartService.addToCart(cartItem);
  }
}
