import { routes } from './../../app.routes';
import { AuthGuard } from './auth.guard';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from './auth.service';

export class AdminGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    if (this.authService.isAuthenticated()) {
      const authorites = this.authService.getAuthorities();
      if (authorites.includes('admin')) {
        return true;
      }
    }
    this.router.navigate(['/']);
    return false;
  }
}
