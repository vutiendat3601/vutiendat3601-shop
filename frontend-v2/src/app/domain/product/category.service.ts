import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageDto } from './../../common/page-dto';
import { Injectable } from '@angular/core';
import { CategoryDto } from './category-dto';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private readonly CATEGORY_API_V2_BASE_URL = `${environment.apiBaseUrl}/v2/categories`;
  constructor(private readonly http: HttpClient) {}

  public getCategories(
    page: number,
    size: number
  ): Observable<PageDto<CategoryDto>> {
    return this.http.get<PageDto<CategoryDto>>(
      `${this.CATEGORY_API_V2_BASE_URL}?page=${page}&size=${size}`
    );
  }
}
