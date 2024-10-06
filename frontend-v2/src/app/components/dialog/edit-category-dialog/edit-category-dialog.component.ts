import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms'; 
import { CategoryDto } from '../../../domain/product/category-dto';

@Component({
  selector: 'app-edit-category-dialog',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatDialogModule, FormsModule],
  templateUrl: './edit-category-dialog.component.html',
  styleUrl: './edit-category-dialog.component.scss'
})
export class EditCategoryDialogComponent {
  category: CategoryDto;

  constructor(
    public dialogRef: MatDialogRef<EditCategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CategoryDto
  ) {
    this.category = data ? { ...data } : new CategoryDto(undefined);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    this.dialogRef.close(this.category);
  }
}
