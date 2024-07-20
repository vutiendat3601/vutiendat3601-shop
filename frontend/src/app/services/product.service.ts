import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Product } from '../models/product';
import { ProductCategory } from '../models/product-category';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/v1/products';
  private categoryUrl = 'http://localhost:8080/v1/product-category';

  constructor(private httpClient: HttpClient) {}

  getProductsList(categoryId: number): Observable<Product[]> {
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?categoryId=${categoryId}`;
    return this.httpClient
      .get<ProductListResponse>(searchUrl)
      .pipe(map((response) => response._embedded.products));
  }

  searchProductsPaginate(
    thePage: number,
    thePageSize: number,
    theKeyword: string
  ): Observable<ProductListResponse> {
    const searchUrl =
      `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}` +
      `&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<ProductListResponse>(searchUrl);
  }

  getProductListPaginate(
    page: number,
    size: number,
    categoryId: number
  ): Observable<ProductListResponse> {
    const searchUrl =
      `${this.baseUrl}/search/findByCategoryId?categoryId=${categoryId}` +
      `&page=${page}&size=${size}`;
    return this.httpClient.get<ProductListResponse>(searchUrl);
  }

  getProductCategories(): Observable<ProductCategory[]> {
    return this.httpClient
      .get<ProductCategoryResponse>(this.categoryUrl)
      .pipe(map((response) => response._embedded.productCategory));
  }

  getProduct(productId: number): Observable<Product> {
    const productUrl = `${this.baseUrl}/${productId}`;
    return this.httpClient.get<Product>(productUrl);
  }

  searchProducts(keyword: string) {
    const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${keyword}`;
    return this.httpClient
      .get<ProductListResponse>(searchUrl)
      .pipe(map((response) => response._embedded.products));
  }
}

interface ProductListResponse {
  _embedded: {
    products: Product[];
  };
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  };
}

interface ProductCategoryResponse {
  _embedded: {
    productCategory: ProductCategory[];
  };
}
