import { Component } from '@angular/core';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrl: './resetpassword.component.css'
})
export class ResetpasswordComponent {
  newPassword: string = '';
  confirmPassword: string = '';

  // Biến để điều khiển hiển thị alert
  alertVisible: boolean = false;
  alertMessage: string = '';

  // Phương thức xác nhận mật khẩu
  onSubmit() {
    if (this.newPassword.length < 8 || this.newPassword.length > 32) {
      this.showAlert('Mật khẩu mới phải có độ dài từ 8 đến 32 ký tự.');
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.showAlert('Mật khẩu xác nhận không khớp.');
      return;
    }

    this.showAlert('Mật khẩu đã được đặt lại thành công!');
  }

  // Hiển thị thông báo tùy chỉnh
  showAlert(message: string): void {
    this.alertMessage = message;
    this.alertVisible = true;
  }

  // Đóng thông báo
  closeAlert(): void {
    this.alertVisible = false;
  }

  // Biến để lưu trạng thái hiển thị mật khẩu
  showPassword: boolean = false;

  // Phương thức chuyển đổi hiển thị/ẩn mật khẩu
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
