import {
  APP_INITIALIZER,
  ApplicationConfig,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import {
  HttpHandlerFn,
  HttpRequest,
  provideHttpClient,
  withFetch,
  withInterceptors,
} from '@angular/common/http';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AuthService } from './domain/auth/auth.service';

export function authServiceFactory(
  authService: AuthService
): () => Promise<void> {
  return () => authService.initialize();
}

const jwtInterceptor = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    },
  });
  return next(authReq);
};

export const appConfig: ApplicationConfig = {
  providers: [
    AuthService,
    {
      provide: APP_INITIALIZER,
      useFactory: authServiceFactory,
      deps: [AuthService],
      multi: true,
    },
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withFetch(), withInterceptors([jwtInterceptor])),
    provideAnimationsAsync(),
  ],
};
