import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CategoryDto } from '../../domain/product/category-dto';
import { CategoryService } from '../../domain/product/category.service';

@Component({
  selector: 'app-category-menu',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './category-menu.component.html',
  styleUrl: './category-menu.component.scss',
})
export class CategoryMenuComponent {
  selectedCategoryCode: string | null = null;
  categoryDtos: CategoryDto[] = [];
  page: number = 1;
  size: number = 50;

  constructor(
    private readonly categoryService: CategoryService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.listCategories();
    this.route.queryParamMap.subscribe(() => {
      if (this.route.snapshot.queryParamMap.has('categoryCode')) {
        this.selectedCategoryCode =
          this.route.snapshot.queryParamMap.get('categoryCode');
      }
    });
  }
  listCategories() {
    this.categoryService
      .getCategories(this.page, this.size)
      .subscribe((categoryDtoPage) => {
        this.categoryDtos = categoryDtoPage.items;
      });
  }
}
