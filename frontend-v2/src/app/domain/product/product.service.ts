import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageDto } from '../../common/page-dto';
import { ProductDto } from './product-dto';
import { environment } from '../../../environments/environment';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly API_PRODUCT_BASE_URL = `${environment.apiBaseUrl}/v2/products`;
  private readonly API_PRODUCT_DETAIL_BASE_URL = `${environment.apiBaseUrl}/v2/product-all`;

  constructor(private http: HttpClient) {}

  public getProductsByCategoryCode(
    categoryCode: string,
    page: number = 1,
    size: number = 50
  ): Observable<PageDto<ProductDto>> {
    return this.http.get<PageDto<ProductDto>>(
      `${this.API_PRODUCT_BASE_URL}?categoryCode=${categoryCode}&page=${page}&size=${size}`
    );
  }

  public getTrendingProducts(
    page: number = 1,
    size: number = 50
  ): Observable<PageDto<ProductDto>> {
    return this.http.get<PageDto<ProductDto>>(
      `${this.API_PRODUCT_BASE_URL}/trending?page=${page}&size=${size}`
    );
  }

  public getProductByProductNo(productNo: string): Observable<ProductDto> {
    return this.http.get<ProductDto>(`${this.API_PRODUCT_DETAIL_BASE_URL}/${productNo}/detail`)
  }
}
