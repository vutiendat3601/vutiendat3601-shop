import { Routes } from '@angular/router';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ProductListComponent } from './components/product-list/product-list/product-list.component';
import { LoginComponent } from './components/auth/login/login.component';
import { SIGNAL } from '@angular/core/primitives/signals';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';

export const routes: Routes = [
  // { path: 'checkout', component: CheckoutComponent },
  // { path: 'cart-details', component: CartDetailsComponent },
  // { path: 'search/:keyword', component: ProductListComponent },
  // { path: 'category/:id', component: ProductListComponent },
  // { path: 'category', component: ProductListComponent },
  // { path: 'products', component: ProductListComponent },
  // { path: 'products/:id', component: ProductDetailsComponent },
  // { path: 'auth/login', component: LoginComponent },
  // { path: 'auth/register', component: RegisterComponent },
  // { path: 'auth/resetpassword', component: ResetpasswordComponent },
  // { path: '', redirectTo: '/products', pathMatch: 'full' },
  // { path: '**', redirectTo: '/products', pathMatch: 'full' },
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', component: ProductListComponent },
      { path: 'cart-detail', component: CartDetailsComponent },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'sign-up',
        component: SignUpComponent,
      },
    ],
  },
];
