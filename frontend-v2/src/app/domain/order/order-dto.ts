import { OrderItemDto } from './order-item-dto';

export class OrderDto {
  constructor(
    public trackingNumber: string,
    public status: string,
    public numberOfProducts: number,
    public totalProductAmount: number,
    public totalProductCouponAmount: number,
    public totalProductFinalAmount: number,
    public vatFeeAmount: number,
    public shippingFeeAmount: number,
    public shippingFeeCouponAmount: number,
    public finalAmount: number,
    public shipingFeeCouponCode: string,
    public shippingAddressCode: string,
    public customerCode: string,
    public items: OrderItemDto[]
  ) {}
}
