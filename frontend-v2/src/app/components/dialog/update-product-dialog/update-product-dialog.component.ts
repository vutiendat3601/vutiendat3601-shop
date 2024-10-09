import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { FormsModule } from '@angular/forms';
import { ProductDto } from '../../../domain/product/product-dto';
import { CategoryService } from '../../../domain/product/category.service';
import { CategoryDto } from '../../../domain/product/category-dto';
import { ProductRequestUpdateDto } from '../../../domain/product/product-request-update-dto';

@Component({
  selector: 'app-update-product-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    FormsModule,
  ],
  templateUrl: './update-product-dialog.component.html',
  styleUrl: './update-product-dialog.component.scss',
})
export class UpdateProductDialogComponent {
  productUpdateReq: ProductRequestUpdateDto;
  categories: any[] = [];
  selectedCategoryId: number | undefined;

  constructor(
    public dialogRef: MatDialogRef<UpdateProductDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductDto,
    private categoryService: CategoryService
  ) {
    this.productUpdateReq = new ProductRequestUpdateDto(
      data.sku,
      data.name,
      data.description,
      data.unitPrice,
      data.thumbnail,
      data.isActive,
      data.categoryId
    );
    if (!data.categoryId) {
      this.selectedCategoryId = data.categoryId;
    }
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
    this.productUpdateReq.categoryId = categoryId;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    this.productUpdateReq.categoryId = this.selectedCategoryId;
    this.dialogRef.close(this.productUpdateReq);
    console.log(this.productUpdateReq);
  }
}
