import { Component } from '@angular/core';

@Component({
  selector: 'app-manage-order',
  standalone: true,
  imports: [],
  templateUrl: './manage-order.component.html',
  styleUrl: './manage-order.component.scss'
})
export class ManageOrderComponent {
// Hàm này sẽ được gọi khi dropdown thay đổi
updateStatusColor(event: Event): void {
  const dropdown = event.target as HTMLSelectElement;

  // Xóa tất cả các class màu sắc trước khi thêm class mới
  dropdown.classList.remove('green-bg', 'red-bg', 'default-bg');

  // Kiểm tra trạng thái và áp dụng màu tương ứng
  if (dropdown.value === 'shipped') {
    dropdown.classList.add('green-bg');
  } else if (dropdown.value === 'canceled') {
    dropdown.classList.add('red-bg');
  } else {
    dropdown.classList.add('default-bg');
  }
}

// Đặt màu mặc định khi trang tải lần đầu
ngOnInit(): void {
  const dropdown = document.getElementById('order-status') as HTMLSelectElement;
  this.updateStatusColor(new Event('change', { bubbles: true, cancelable: true }));
}
}


