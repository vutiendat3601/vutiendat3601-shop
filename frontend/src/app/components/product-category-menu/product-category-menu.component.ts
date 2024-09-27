import { Component, OnInit } from '@angular/core';
import { ProductCategory } from '../../models/product-category';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-category-menu',
  templateUrl: './product-category-menu.component.html',
  styleUrl: './product-category-menu.component.css',
})
export class ProductCategoryMenuComponent implements OnInit {
  selectedCategoryId: number | null = null;
  productCategories: ProductCategory[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.listProductCategories();
  }
  listProductCategories() {
    this.productService.getProductCategories().subscribe((data) => {
      this.productCategories = data;
    });
  }
  isSelected(categoryId: number): boolean {
    return this.selectedCategoryId === categoryId;
  }
  onCategorySelect(categoryId: number) {
    this.selectedCategoryId = categoryId;
  }
}
