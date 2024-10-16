import { CurrencyPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PageDto } from '../../common/page-dto';
import { CreateOrderPaymentRequest } from '../../domain/order/create-order-payment-request';
import { OrderDto } from '../../domain/order/order-dto';
import { OrderService } from '../../domain/order/order.service';

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

  pay(orderDto: OrderDto) {
    this.orderService
      .createOrderPayment(
        orderDto.trackingNumber,
        new CreateOrderPaymentRequest('VN_PAY')
      )
      .subscribe((orderPaymentDto) => {
        window.location.href = orderPaymentDto.paymentUrl;
      });
  }
}
