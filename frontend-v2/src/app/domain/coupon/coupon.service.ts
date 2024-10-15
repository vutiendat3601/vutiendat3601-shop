import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PageDto } from './../../common/page-dto';
import { CouponDto } from './coupon-dto';

@Injectable({
  providedIn: 'root',
})
export class CouponService {
  private readonly API_COUPON_BASE_URL = `${environment.apiBaseUrl}/v2/coupons`;
  constructor(private http: HttpClient) {}

  public getAvailableShippingFeeCoupons(): Observable<PageDto<CouponDto>> {
    return this.http.get<PageDto<CouponDto>>(
      `${this.API_COUPON_BASE_URL}/shipping-fees`
    );
  }
}
