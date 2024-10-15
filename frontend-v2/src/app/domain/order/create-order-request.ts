import { CreateOrderItemDto } from './create-order-item-dto';

export class CreateOrderRequest {
  constructor(
    public addressCode: string,
    public shippingFeeCouponCode: string | null,
    public items: CreateOrderItemDto[]
  ) {}
}
