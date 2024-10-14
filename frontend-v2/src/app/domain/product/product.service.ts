import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageDto } from '../../common/page-dto';
import { ProductDto } from './product-dto';
import { environment } from '../../../environments/environment';
import { ProductRequestCreateDto } from './product-request-create-dto';
import { ProductRequestUpdateDto } from './product-request-update-dto';
import { CouponDto } from '../coupon/coupon-dto';
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
    return this.http.get<ProductDto>(
      `${this.API_PRODUCT_DETAIL_BASE_URL}/${productNo}/detail?`
    );
  }

  public getCouponsByProductNo(
    productNo: string,
    page: number = 1,
    size: number = 100
  ): Observable<PageDto<CouponDto>> {
    return this.http.get<PageDto<CouponDto>>(
      `${this.API_PRODUCT_BASE_URL}/${productNo}/coupons?page=${page}&size=${size}`
    );
  }

  public addProduct(
    productCreateReq: ProductRequestCreateDto
  ): Observable<void> {
    return this.http.post<void>(
      `${this.API_PRODUCT_BASE_URL}`,
      productCreateReq
    );
  }

  public updateProduct(
    productNo: string,
    productUpdateReq: ProductRequestUpdateDto
  ): Observable<void> {
    return this.http.put<void>(
      `${this.API_PRODUCT_BASE_URL}/${productNo}`,
      productUpdateReq
    );
  }

  public deleteProduct(productNo: string): Observable<void> {
    return this.http.delete<void>(`${this.API_PRODUCT_BASE_URL}/${productNo}`);
  }
}
