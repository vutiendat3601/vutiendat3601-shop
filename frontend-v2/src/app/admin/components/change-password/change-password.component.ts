import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.scss'
})
export class ChangePasswordComponent {
  newPassword: string = '';
  confirmPassword: string = '';
  passwordsMatch: boolean = true;
  confirmPasswordFocused: boolean = false;
  alertVisible: boolean = false;
  alertMessage: string = '';
  showPassword: boolean = false;

  validatePasswordMatch(): void {
    this.passwordsMatch = this.newPassword === this.confirmPassword;
  }

  onSubmit() {
    if (!this.passwordsMatch) {
      this.showAlert('Mật khẩu xác nhận không khớp.');
      return;
    }

    if (this.newPassword.length < 8 || this.newPassword.length > 32) {
      this.showAlert('Mật khẩu mới phải có độ dài từ 8 đến 32 ký tự.');
      return;
    }
  }

  showAlert(message: string): void {
    this.alertMessage = message;
    this.alertVisible = true;
  }

  closeAlert(): void {
    this.alertVisible = false;
  }

  togglePassword(inputId: string): void {
    const inputElement = document.getElementById(inputId) as HTMLInputElement;
    const iconElement = inputElement.nextElementSibling as HTMLElement;

    if (inputElement.type === 'password') {
      inputElement.type = 'text';
      iconElement.classList.remove('fa-eye-slash');
      iconElement.classList.add('fa-eye');
    } else {
      inputElement.type = 'password';
      iconElement.classList.remove('fa-eye');
      iconElement.classList.add('fa-eye-slash');
    }
  }
}
