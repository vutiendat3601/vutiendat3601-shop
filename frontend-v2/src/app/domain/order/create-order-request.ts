import { CreateOrderItemDto } from './create-order-item-dto';

export class CreateOrderRequest {
  constructor(
    public wardId: number,
    public shippingFeeCouponCode: string | null,
    public items: CreateOrderItemDto[]
  ) {}
}
