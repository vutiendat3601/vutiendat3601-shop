import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { OrderDto } from '../../../domain/order/order-dto';
import { OrderService } from '../../../domain/order/order.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UpdateOrderStatus } from '../../../domain/order/update-order-status';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../components/dialog/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-manage-order',
  standalone: true,
  imports: [RouterLink, CurrencyPipe, FormsModule, CommonModule],
  templateUrl: './manage-order.component.html',
  styleUrl: './manage-order.component.scss',
})
export class ManageOrderComponent implements OnInit {
  orderDtos: OrderDto[] = [];
  page: number = 1;
  size: number = 20;

  constructor(
    private readonly orderService: OrderService,
    private readonly dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.listOrders();
  }

  listOrders(): void {
    this.orderService
      .getOrdersByAdmin(this.page, this.size)
      .subscribe((orderDtoPage) => {
        this.orderDtos = orderDtoPage.items;
      });
  }

  onSelectedChange(trackingNumber: string, status: string) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Bạn có chắc chắn muốn thay đổi trạng thái?',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.orderService
          .updateOrderStatus(trackingNumber, new UpdateOrderStatus(status))
          .subscribe({
            next: () => {
              this.listOrders();
            },
          });
      } else {
        this.listOrders();
      }
    });
  }
}
