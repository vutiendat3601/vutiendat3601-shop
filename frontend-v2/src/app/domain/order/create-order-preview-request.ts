import { CreateOrderItemDto } from './create-order-item-dto';

export class CreateOrderPreviewRequest {
  constructor(
    public wardId: number | null,
    public shippingFeeCouponCode: string | null,
    public items: CreateOrderItemDto[]
  ) {}
}
