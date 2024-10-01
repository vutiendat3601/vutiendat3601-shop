import { Component } from '@angular/core';
import { CartStatusComponent } from '../../../components/cart-status/cart-status.component';
import { SearchComponent } from '../../../components/search/search.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [SearchComponent, CartStatusComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {}
