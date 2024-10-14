import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CouponService {
  private readonly API_COUPON_BASE_URL = `${environment.apiBaseUrl}/v2/orders`;
  constructor(private http: HttpClient) {}
}
