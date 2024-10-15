import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './admin/admin/layouts/admin-layout/admin-layout.component';
import { ManageCategoryComponent } from './admin/components/manage-category/manage-category.component';
import { ManageOrderComponent } from './admin/components/manage-order/manage-order.component';
import { ManageProductComponent } from './admin/components/manage-product/manage-product.component';
import { LoginComponent } from './components/auth/login/login.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { ResetpasswordComponent } from './components/auth/resetpassword/resetpassword.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OrderListComponent } from './components/order-list/order-list.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { VnPayResultComponent } from './components/payment/vn-pay-result/vn-pay-result.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { ChangePasswordComponent } from './admin/components/change-password/change-password.component';
import { AuthGuard } from './domain/auth/auth.guard';

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
      // {
      //   path: 'checkout',
      //   component: CheckoutComponent,
      // },
      {
        path: 'order-list',
        component: OrderListComponent,
      },
      {
        path: 'order/payment/callback/vnpay',
        component: VnPayResultComponent,
      },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        canActivate: [AuthGuard],
        component: LoginComponent,
      },
      { path: 'sign-up', component: SignUpComponent },
      { path: 'resetpassword', component: ResetpasswordComponent },
    ],
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', component: ProductListComponent },
      { path: 'manage-product', component: ManageProductComponent },
      { path: 'manage-category', component: ManageCategoryComponent },
      { path: 'manage-order', component: ManageOrderComponent },
      { path: 'manage-user', component: ProductListComponent },
      { path: 'manage-sales', component: ProductListComponent },
      { path: 'logout', component: CartDetailsComponent },
      { path: 'change-password', component: ChangePasswordComponent },
    ],
  },
];
