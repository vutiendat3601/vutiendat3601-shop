export class CartItem {
  constructor(
    public name: string,
    public productNo: string,
    public productThumbnail: string | null,
    public unitPrice: number,
    public quantity: number,
    public categoryCode: string,
    public shippingFee: number | null,
    public couponCode: string | null,
    public couponAmount: number | null,
    public finalAmount: number | null
  ) {}
}
