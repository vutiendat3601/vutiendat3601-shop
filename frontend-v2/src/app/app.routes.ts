import { Routes } from '@angular/router';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { LoginComponent } from './components/auth/login/login.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AdminLayoutComponent } from './admin/admin/layouts/admin-layout/admin-layout.component';
import { ManageCategoryComponent } from './admin/components/manage-category/manage-category.component';

export const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', component: ProductListComponent },
      { path: 'cart-details', component: CartDetailsComponent },
      {
        path: 'products/:productNo',
        component: ProductDetailsComponent,
      },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'sign-up', component: SignUpComponent },
    ],
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: 'dashboard', component: ProductListComponent },
      { path: 'manage-product', component: ProductListComponent },
      { path: 'manage-category', component: ManageCategoryComponent },
      { path: 'manage-order', component: ProductListComponent },
      { path: 'manage-user', component: ProductListComponent },
      { path: 'manage-sales', component: ProductListComponent },
      { path: 'logout', component: CartDetailsComponent },
      { path: 'change-password', component: CartDetailsComponent },
    ],
  },
];
