import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PageDto } from '../../common/page-dto';
import { AuthService } from '../auth/auth.service';
import { CreateOrderPaymentRequest } from './create-order-payment-request';
import { CreateOrderPreviewRequest } from './create-order-preview-request';
import { CreateOrderRequest } from './create-order-request';
import { OrderDto } from './order-dto';
import { OrderPaymentDto } from './order-payment-dto';
import { UpdateOrderStatus } from './update-order-status';
import { VnPayPaymentResult } from './vn-pay-payment-result';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private readonly API_ORDER_BASE_URL = `${environment.apiBaseUrl}/v2/orders`;
  private readonly API_ORDER_ADMIN_BASE_URL = `${environment.apiBaseUrl}/v2/admin/orders`;

  constructor(
    private readonly http: HttpClient,
    private readonly authService: AuthService
  ) {}

  public getOrderPreview(
    createOrderPreviewReq: CreateOrderPreviewRequest
  ): Observable<OrderDto> {
    console.log(createOrderPreviewReq);
    return this.http.post<OrderDto>(
      `${this.API_ORDER_BASE_URL}/preview`,
      createOrderPreviewReq
    );
  }

  public createOrder(createOrderReq: CreateOrderRequest): Observable<OrderDto> {
    return this.http.post<OrderDto>(
      `${this.API_ORDER_BASE_URL}`,
      createOrderReq
    );
  }

  public createOrderPayment(
    trackingNumber: string,
    createOrderPaymentReq: CreateOrderPaymentRequest
  ): Observable<OrderPaymentDto> {
    return this.http.post<OrderPaymentDto>(
      `${this.API_ORDER_BASE_URL}/${trackingNumber}/payment`,
      createOrderPaymentReq,
      {
        headers: {
          Authorization: `Bearer ${this.authService.getJwtToken()}`,
        },
      }
    );
  }

  public getOrders(page: number, size: number): Observable<PageDto<OrderDto>> {
    return this.http.get<PageDto<OrderDto>>(
      `${this.API_ORDER_BASE_URL}?page=${page}&size=${size}`,
      {
        headers: {
          Authorization: `Bearer ${this.authService.getJwtToken()}`,
        },
      }
    );
  }

  public processOrderPaymentResult(vnPayPaymentResult: VnPayPaymentResult) {
    return this.http.post<void>(
      `${this.API_ORDER_BASE_URL}/payment/vnpay`,
      vnPayPaymentResult,
      {
        headers: {
          Authorization: `Bearer ${this.authService.getJwtToken()}`,
        },
      }
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

  public updateOrderStatus(
    trackingNumber: string,
    updateOrderStatus: UpdateOrderStatus
  ): Observable<void> {
    return this.http.put<void>(
      `${this.API_ORDER_ADMIN_BASE_URL}/${trackingNumber}`,
      updateOrderStatus
    );
  }
}
