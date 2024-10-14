import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { CreateOrderRequest } from './create-order-request';
import { OrderDto } from './order-dto';
import { Observable } from 'rxjs';
import { PageDto } from '../../common/page-dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private readonly API_ORDER_BASE_URL = `${environment.apiBaseUrl}/v2/orders`;
  private readonly API_ORDER_ADMIN_BASE_URL = `${environment.apiBaseUrl}/v2/admin/orders`;

  constructor(private http: HttpClient) {}

  public getOrderPreview(
    createOrderReq: CreateOrderRequest
  ): Observable<OrderDto> {
    console.log(createOrderReq);
    return this.http.post<OrderDto>(
      `${this.API_ORDER_BASE_URL}/preview`,
      createOrderReq
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
