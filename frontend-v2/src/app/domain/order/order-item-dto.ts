export class OrderItemDto {
  constructor(
    public orderTrackingNumber: string,
    public quantity: number,
    public totalAmount: number,
    public couponAmount: number,
    public finalAmount: number,
    public productNo: string,
    public productName: string,
    public couponCode: string
  ) {}
}
