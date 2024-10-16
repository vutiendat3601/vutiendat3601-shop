import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtVerify } from 'jose';
import {
  BehaviorSubject,
  catchError,
  firstValueFrom,
  map,
  Observable,
  of,
  Subject,
  throwError,
} from 'rxjs';
import { environment } from '../../../environments/environment';
import { TokenDto } from './token-dto';
import { TokenRequest } from './token-request';
import { UserAuthentication } from './user-authentication';
import { VerificationDto } from './verification-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_AUTH_BASE_URL = `${environment.apiBaseUrl}/v2/auth`;
  private currentUser: UserAuthentication | null = null;
  private token: TokenDto | null = null;
  private publicKey: string | null = null;

  private currentUserSubject: Subject<UserAuthentication | null> =
    new BehaviorSubject<UserAuthentication | null>(null);
  constructor(private readonly http: HttpClient) {
    // this.initialize();
  }

  public getJwtToken(): string | undefined {
    return this.token?.token;
  }

  async initialize() {
    this.publicKey = await this.initalizePublicKey();
    const tokenDtoStr = localStorage.getItem('token');
    if (tokenDtoStr) {
      this.token = JSON.parse(tokenDtoStr);
      if (this.token) {
        await this.verifyToken(this.token.token);
      }
    }
  }

  public async initalizePublicKey(): Promise<string> {
    return await firstValueFrom(
      this.http.get(`${this.API_AUTH_BASE_URL}/public-key`, {
        responseType: 'text',
      })
    );
  }

  public currentUserChanged(): Observable<UserAuthentication | null> {
    return this.currentUserSubject;
  }

  public login(
    username: string,
    password: string
  ): Observable<VerificationDto> {
    return this.http
      .post<VerificationDto>(
        `${this.API_AUTH_BASE_URL}/login`,
        {},
        {
          headers: {
            Authorization: `Basic ${btoa(`${username}:${password}`)}`,
          },
        }
      )
      .pipe(
        catchError(() => throwError(() => new Error('Đăng nhập thất bại.')))
      );
  }

  public isAuthenticated(): boolean {
    return this.currentUser !== null;
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
          this.verifyToken(tokenDto.token);
          return true;
        }),
        catchError((_err) => of(false))
      );
  }

  public logout(): void {
    localStorage.removeItem('token');
    this.currentUser = null;
    this.currentUserSubject.next(null);
  }

  private async verifyToken(token: string): Promise<boolean> {
    try {
      if (this.publicKey) {
        const key = await this.importPublicKey(this.publicKey);
        const { payload } = await jwtVerify(token, key);
        this.currentUser = payload['user'] as UserAuthentication;
        if (this.currentUser) {
          this.currentUserSubject.next(this.currentUser);
          return true;
        }
      }
    } catch (error) {
      console.error('JWT verification failed:', error);
    }
    return false;
  }

  private async importPublicKey(pemKey: string): Promise<CryptoKey> {
    const pemHeader = '-----BEGIN PUBLIC KEY-----';
    const pemFooter = '-----END PUBLIC KEY-----';
    const pemContents = pemKey.substring(
      pemHeader.length,
      pemKey.length - pemFooter.length
    );
    const binaryDerString = atob(pemContents.replace(/\s+/g, ''));
    const binaryDer = this.str2ab(binaryDerString);

    return await crypto.subtle.importKey(
      'spki',
      binaryDer,
      {
        name: 'RSASSA-PKCS1-v1_5',
        hash: 'SHA-256',
      },
      true,
      ['verify']
    );
  }

  // Helper function to convert string to ArrayBuffer
  private str2ab(str: string): ArrayBuffer {
    const buf = new ArrayBuffer(str.length);
    const bufView = new Uint8Array(buf);
    for (let i = 0, strLen = str.length; i < strLen; i++) {
      bufView[i] = str.charCodeAt(i);
    }
    return buf;
  }
}
