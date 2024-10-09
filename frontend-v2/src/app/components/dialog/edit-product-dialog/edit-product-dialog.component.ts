import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { ProductDto } from '../../../domain/product/product-dto';
import { CategoryService } from '../../../domain/product/category.service';
import { CategoryDto } from '../../../domain/product/category-dto';
import { ProductRequestCreateDto } from '../../../domain/product/product-request-create-dto';

@Component({
  selector: 'app-edit-product-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    FormsModule,
  ],
  templateUrl: './edit-product-dialog.component.html',
  styleUrls: ['./edit-product-dialog.component.scss'],
})
export class EditProductDialogComponent implements OnInit {
  productCreateReq: ProductRequestCreateDto;
  categories: any[] = [];
  selectedCategoryId: number | undefined;

  constructor(
    public dialogRef: MatDialogRef<EditProductDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductDto,
    private categoryService: CategoryService
  ) {
    this.productCreateReq = data ? { ...data } : new ProductRequestCreateDto();
    this.selectedCategoryId = this.productCreateReq.categoryId;
  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.getCategories(1, 50).subscribe((data) => {
      this.categories = data['items']
        .filter(
          (category: CategoryDto): category is CategoryDto =>
            category.id !== undefined
        )
        .map((category: CategoryDto) => ({
          id: category.id!,
          name: category.name,
        }));
    });
  }

  onCategoryChange(categoryId: number): void {
    this.productCreateReq.categoryId = categoryId;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    this.productCreateReq.categoryId = this.selectedCategoryId;
    this.dialogRef.close(this.productCreateReq);
  }
}
