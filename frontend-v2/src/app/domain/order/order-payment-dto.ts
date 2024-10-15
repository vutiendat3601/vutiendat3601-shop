export class OrderPaymentDto {
  constructor(
    public trackingNumber: string,
    public ref: string,
    public amount: number,
    public status: string,
    public message: string,
    public errorMessage: string,
    public paymentUrl: string,
    public paymentUrlExpiredAt: string,
    public callbackUrl: string
  ) {}
}
