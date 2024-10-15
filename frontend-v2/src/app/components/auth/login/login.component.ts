import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../domain/auth/auth.service';
import { TokenDto } from '../../../domain/auth/token-dto';

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
  ) {
    // fb: FormBuilder
    // this.loginForm = this.fb.group({
    // username: ['', Validators.required],
    // password: ['', [Validators.required, Validators.minLength(8)]],
    // });
  }

  ngOnInit(): void {}

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    if (this.loginFormGroup.valid) {
      const username = this.loginFormGroup.get('username')?.value;
      const password = this.loginFormGroup.get('password')?.value;
      if (username && password) {
        this.authService.login(username, password).subscribe((verifDto) => {
          this.authService
            .getToken({ code: verifDto.code })
            .subscribe((tokenDto: TokenDto) => {
              this.router.navigate(['/']);
            });
        });
      }
    }
    console.log('Thông tin đăng nhập không hợp lệ');
  }
}
