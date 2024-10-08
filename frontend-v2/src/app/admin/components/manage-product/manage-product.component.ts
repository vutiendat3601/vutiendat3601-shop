import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryMenuComponent } from '../../../components/category-menu/category-menu.component';
import { ProductService } from '../../../domain/product/product.service';
import { PageDto } from '../../../common/page-dto';
import { ProductDto } from '../../../domain/product/product-dto';
import { EditProductDialogComponent } from '../../../components/dialog/edit-product-dialog/edit-product-dialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../components/dialog/confirm-dialog/confirm-dialog.component';
import { ProductRequestCreateDto } from '../../../domain/product/product-request-create-dto';
import { UpdateProductDialogComponent } from '../../../components/dialog/update-product-dialog/update-product-dialog.component';
import { ProductRequestUpdateDto } from '../../../domain/product/product-request-update-dto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-product',
  standalone: true,
  imports: [
    CategoryMenuComponent,
    RouterLink,
    NgbPaginationModule,
    CurrencyPipe,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './manage-product.component.html',
  styleUrl: './manage-product.component.scss',
})
export class ManageProductComponent {
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
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly dialog: MatDialog
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
    this.router.navigate([], {
      queryParams: { categoryCode: this.selectedCategoryCode },
      queryParamsHandling: 'merge',
    });
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

  addProduct(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.maxWidth = 'none';
    dialogConfig.height = '90%';
    const dialogRef = this.dialog.open(
      EditProductDialogComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((result: ProductRequestCreateDto) => {
      if (result) {
        this.productService.addProduct(result).subscribe({
          next: () => {
            this.listProducts();
          },
        });
      }
    });
  }

  updateProduct(product: ProductDto): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.maxWidth = 'none';
    dialogConfig.data = { ...product };

    const dialogRef = this.dialog.open(
      UpdateProductDialogComponent,
      dialogConfig
    );

    dialogRef
      .afterClosed()
      .subscribe((updatedProduct: ProductRequestUpdateDto) => {
        if (updatedProduct && product.productNo) {
          this.productService
            .updateProduct(product.productNo, updatedProduct)
            .subscribe(() => {
              this.listProducts();
            });
        }
      });
  }

  deleteProduct(productNo: string | undefined): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Bạn có chắc chắn muốn xóa sản phẩm này không?',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && productNo) {
        this.productService.deleteProduct(productNo).subscribe({
          next: () => {
            this.listProducts();
          },
        });
      }
    });
  }
}
