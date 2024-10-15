import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TokenDto } from './token-dto';
import { TokenRequest } from './token-request';
import { VerificationDto } from './verification-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_AUTH_BASE_URL = `${environment.apiBaseUrl}/v2/auth`;

  // private   = `${this.API_AUTH_BASE_URL}/login`;
  constructor(private readonly http: HttpClient) {}

  public login(
    username: string,
    password: string
  ): Observable<VerificationDto> {
    return this.http.post<VerificationDto>(
      `${this.API_AUTH_BASE_URL}/login`,
      {},
      {
        headers: {
          Authorization: `Basic ${btoa(`${username}:${password}`)}`,
        },
      }
    );
  }

  public getToken(tokenReq: TokenRequest): Observable<boolean> {
    const formUrlEncoded = new URLSearchParams();
    formUrlEncoded.set('code', tokenReq.code);
    return this.http
      .post<TokenDto>(
        `${this.API_AUTH_BASE_URL}/token`,
        formUrlEncoded.toString(),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        }
      )
      .pipe(
        map((tokenDto) => {
          localStorage.setItem('token', JSON.stringify(tokenDto));
          return true;
        }),
        catchError((_err) => of(false))
      );
  }
}
