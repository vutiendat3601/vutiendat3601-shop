import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { OrderDto } from '../../../domain/order/order-dto';
import { OrderService } from '../../../domain/order/order.service';
import {  CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

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
  
  @ViewChild('orderStatusDropdown', { static: false }) dropdown: ElementRef<HTMLSelectElement> | undefined;

  constructor(private readonly orderService: OrderService) {}

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

  updateStatusColor(event: Event, dropdown: HTMLSelectElement): void {
    dropdown.classList.remove('green-bg', 'red-bg', 'default-bg');
    if (dropdown.value === 'SHIPPED') {
      dropdown.classList.add('green-bg');
    } else if (dropdown.value === 'CANCELED') {
      dropdown.classList.add('red-bg');
    } else {
      dropdown.classList.add('default-bg');
    }
  }
}
