import { Component } from '@angular/core';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
  isVerified: boolean = true; // Hiện mặc định là true


  get iconClass(): string {
    return this.isVerified ? 'fa fa-check-circle-o' : 'fa fa-times';
  }

  get noti(): string {
    return this.isVerified ? 'correct' : 'error';
  }

  get headingText():string{
    return this.isVerified ? "Xác thực Email thành công" : "Lỗi xác thực";
  }

  get paragraphText():string{
    return this.isVerified ? "Địa chỉ Email của bạn đã được xác thực thành công." : "Xảy ra lỗi trong quá trình xác thực địa chỉ Email của bạn";
  }

}
