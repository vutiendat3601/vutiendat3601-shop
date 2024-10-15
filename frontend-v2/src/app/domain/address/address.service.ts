import { CreateAddressRequest } from './create-address-request';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { PageDto } from '../../common/page-dto';
import { ProvinceDto } from './province-dto';
import { DistrictDto } from './district-dto';
import { WardDto } from './ward-dto';
import { AddressDetailDto } from './address-detail-dto';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private readonly API_ADDRESS_BASE_URL = `${environment.apiBaseUrl}/v2/addresses`;

  constructor(private http: HttpClient) {}

  public getProvinces(
    page: number = 1,
    size: number = 50
  ): Observable<PageDto<ProvinceDto>> {
    return this.http.get<PageDto<ProvinceDto>>(
      `${this.API_ADDRESS_BASE_URL}/provinces?page=${page}&size=${size}`
    );
  }

  public getDistricts(
    provinceId: number,
    page: number = 1,
    size: number = 50
  ): Observable<PageDto<DistrictDto>> {
    return this.http.get<PageDto<DistrictDto>>(
      `${this.API_ADDRESS_BASE_URL}/provinces/${provinceId}/districts?page=${page}&size=${size}`
    );
  }

  public getWards(
    districtId: number,
    page: number = 1,
    size: number = 50
  ): Observable<PageDto<WardDto>> {
    return this.http.get<PageDto<WardDto>>(
      `${this.API_ADDRESS_BASE_URL}/districts/${districtId}/wards?page=${page}&size=${size}`
    );
  }

  public createAddress(
    createAddrReq: CreateAddressRequest
  ): Observable<AddressDetailDto> {
    return this.http.post<AddressDetailDto>(
      `${this.API_ADDRESS_BASE_URL}`,
      createAddrReq
    );
  }
}
