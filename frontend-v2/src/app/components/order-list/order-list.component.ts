import { Component, OnInit } from '@angular/core';
import { PageDto } from '../../common/page-dto';
import { OrderDto } from '../../domain/order/order-dto';
import { OrderService } from '../../domain/order/order.service';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss',
})
export class OrderListComponent implements OnInit {
  orders: OrderDto[] = [];

  constructor(private readonly orderService: OrderService) {}
  ngOnInit(): void {
    this.orderService
      .getOrders(1, 100)
      .subscribe((orderDtoPage: PageDto<OrderDto>) => {
        this.orders = orderDtoPage.items;
        console.log(this.orders);
      });
  }
}
