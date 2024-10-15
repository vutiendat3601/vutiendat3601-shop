import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../domain/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  // loginForm: FormGroup;
  showPassword: boolean = false;

  loginFormGroup = new FormGroup({
    username: new FormControl<string | null>(null, [Validators.required]),
    password: new FormControl<string | null>(null, [Validators.required]),
  });

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
    }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    if (this.loginFormGroup.valid) {
      const username = this.loginFormGroup.get('username')?.value;
      const password = this.loginFormGroup.get('password')?.value;
      if (username && password) {
        this.authService.login(username, password).subscribe({
          next: (verifDto) => {
            this.authService
              .getToken({ code: verifDto.code })
              .subscribe(() => this.router.navigate(['/']));
          },
          error: () => alert('Đăng nhập thất bại'),
        });
      }
    }
  }
}
