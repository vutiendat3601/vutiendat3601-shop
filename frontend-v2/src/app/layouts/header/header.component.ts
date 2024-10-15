import { AuthService } from './../../domain/auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { CartStatusComponent } from '../../components/cart-status/cart-status.component';
import { SearchComponent } from '../../components/search/search.component';
import { RouterLink } from '@angular/router';
import { UserAuthentication } from '../../domain/auth/user-authentication';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [SearchComponent, CartStatusComponent, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  userAuth: UserAuthentication | null = null;
  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    this.authService
      .currentUserChanged()
      .subscribe((userAuth) => (this.userAuth = userAuth));
  }

  logout(): void {
    this.authService.logout();
  }
}
