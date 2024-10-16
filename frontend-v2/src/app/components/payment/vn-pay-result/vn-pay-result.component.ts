import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../domain/order/order.service';
import { VnPayPaymentResult } from '../../../domain/order/vn-pay-payment-result';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vn-pay-result',
  standalone: true,
  imports: [],
  templateUrl: './vn-pay-result.component.html',
  styleUrl: './vn-pay-result.component.scss',
})
export class VnPayResultComponent implements OnInit {
  constructor(
    private readonly orderService: OrderService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.orderService
      .processOrderPaymentResult(new VnPayPaymentResult(location.href))
      .subscribe(() => this.router.navigate(['order-list']));
  }
}
