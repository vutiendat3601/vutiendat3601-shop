export class CreateOrderItemDto {
  constructor(
    public productNo: string,
    public quantity: number,
    public couponCode: string | null
  ) {}
}
