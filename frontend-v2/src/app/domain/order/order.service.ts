import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PageDto } from '../../common/page-dto';
import { CreateOrderPreviewRequest } from './create-order-preview-request';
import { OrderDto } from './order-dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private readonly API_ORDER_BASE_URL = `${environment.apiBaseUrl}/v2/orders`;
  private readonly API_ORDER_ADMIN_BASE_URL = `${environment.apiBaseUrl}/v2/admin/orders`;

  constructor(private http: HttpClient) {}

  public getOrderPreview(
    createOrderPreviewReq: CreateOrderPreviewRequest
  ): Observable<OrderDto> {
    console.log(createOrderPreviewReq);
    return this.http.post<OrderDto>(
      `${this.API_ORDER_BASE_URL}/preview`,
      createOrderPreviewReq
    );
  }

  public getOrdersByAdmin(
    page: number,
    size: number
  ): Observable<PageDto<OrderDto>> {
    return this.http.get<PageDto<OrderDto>>(
      `${this.API_ORDER_ADMIN_BASE_URL}?page=${page}&size=${size}`
    );
  }
}
