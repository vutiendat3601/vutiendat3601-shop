import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CategoryService } from '../../domain/product/category.service';
import { CategoryDto } from '../../domain/product/category-dto';

@Component({
  selector: 'app-category-menu',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './category-menu.component.html',
  styleUrl: './category-menu.component.scss',
})
export class CategoryMenuComponent {
  selectedCategoryCode: string | null = null;
  categories: CategoryDto[] = [];
  page: number = 1;
  size: number = 50;

  constructor(private readonly categoryService: CategoryService) {}

  ngOnInit(): void {
    this.listCategories();
  }
  listCategories() {
    this.categoryService
      .getCategories(this.page, this.size)
      .subscribe((categoryDtoPage) => {
        this.categories = categoryDtoPage.items;
      });
  }
  isSelected(categoryCode: string): boolean {
    return this.selectedCategoryCode === categoryCode;
  }
  onCategorySelect(categoryCode: string) {
    this.selectedCategoryCode = categoryCode;
  }
}
