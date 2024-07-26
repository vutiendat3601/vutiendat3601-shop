import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { Country } from '../models/country';
import { Purchase } from '../models/purchase';
import { State } from '../models/state';

@Injectable({
  providedIn: 'root',
})
export class CheckoutService {
  private countriesUrl = 'http://localhost:8080/v1/countries';
  private statesUrl = 'http://localhost:8080/v1/states';
  private purchaseUrl = 'http://localhost:8080/api/checkout/purchase';

  constructor(private httpClient: HttpClient) {}

  getCreditCardMonths(startMonth: number): Observable<number[]> {
    let months: number[] = [];
    for (let month = startMonth; month <= 12; month++) {
      months.push(month);
    }
    return of(months);
  }
  getCreditCardYears(): Observable<number[]> {
    let years: number[] = [];
    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;
    for (let year = startYear; year <= endYear; year++) {
      years.push(year);
    }
    return of(years);
  }
  getStates(countryCode: string): Observable<State[]> {
    const searchUrl = `${this.statesUrl}/search/findByCountryCode?code=${countryCode}`;
    return this.httpClient
      .get<StateListResponse>(searchUrl)
      .pipe(map((response) => response._embedded.states));
  }
  getCountries(): Observable<Country[]> {
    return this.httpClient
      .get<CountryListResponse>(this.countriesUrl)
      .pipe(map((response) => response._embedded.countries));
  }
  placeOrder(purchase: Purchase): Observable<any> {
    return this.httpClient.post<Purchase>(this.purchaseUrl, purchase);
  }
}

interface StateListResponse {
  _embedded: { states: State[] };
}
interface CountryListResponse {
  _embedded: { countries: Country[] };
}
