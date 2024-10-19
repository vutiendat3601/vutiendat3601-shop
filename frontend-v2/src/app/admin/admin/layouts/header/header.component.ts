import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserAuthentication } from '../../../../domain/auth/user-authentication';
import { AuthService } from '../../../../domain/auth/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  userAuth: UserAuthentication | null = null;

  constructor(private readonly authService: AuthService) { }

  ngOnInit(): void {
    this.authService
      .currentUserChanged()
      .subscribe((userAuth) => (this.userAuth = userAuth));
  }

  logout(): void {
    this.authService.logout();
  }
}
