import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Banner } from '../models/banner';

@Injectable({
  providedIn: 'root'
})
export class BannerService {
  private baseUrl = 'http://localhost:8080/v1/banners';

  constructor(private httpClient: HttpClient) {}

  getBanners(): Observable<Banner[]> {
    const searchUrl = `${this.baseUrl}`;
    return this.httpClient.get<Banner[]>(searchUrl);
  }
}
