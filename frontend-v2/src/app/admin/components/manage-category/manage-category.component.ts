import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CategoryDto } from '../../../domain/product/category-dto';
import { CategoryService } from '../../../domain/product/category.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../components/dialog/confirm-dialog/confirm-dialog.component';
import { EditCategoryDialogComponent } from '../../../components/dialog/edit-category-dialog/edit-category-dialog.component';

@Component({
  selector: 'app-manage-category',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './manage-category.component.html',
  styleUrl: './manage-category.component.scss',
})
export class ManageCategoryComponent {
  categoryDtos: CategoryDto[] = [];
  page: number = 1;
  size: number = 20;
  categoryDto?: CategoryDto;
  categoryId?: number;

  constructor(
    private readonly categoryService: CategoryService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.listCategories();
  }

  listCategories(): void {
    this.categoryService
      .getCategories(this.page, this.size)
      .subscribe((categoryDtoPage) => {
        this.categoryDtos = categoryDtoPage.items;
      });
  }

  addCategory(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.maxWidth = 'none';
    const dialogRef = this.dialog.open(
      EditCategoryDialogComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((result: CategoryDto) => {
      if (result) {
        this.categoryService.addCategory(result).subscribe({
          next: () => {
            this.listCategories();
          },
        });
      }
    });
  }

  updateCategory(category: CategoryDto): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.maxWidth = 'none';
    dialogConfig.data = { ...category };
    const dialogRef = this.dialog.open(
      EditCategoryDialogComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((updatedCategory: CategoryDto) => {
      if (updatedCategory && updatedCategory.code) {
        this.categoryService
          .updateCategory(updatedCategory.code, updatedCategory)
          .subscribe(() => {
            this.listCategories();
          });
      }
    });
  }

  deleteCategory(categoryCode: string | undefined): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Bạn có chắc chắn muốn xóa danh mục này không?',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && categoryCode) {
        this.categoryService.deleteCategory(categoryCode).subscribe({
          next: () => {
            this.listCategories();
          },
        });
      }
    });
  }
}
