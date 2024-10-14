export class CartItem {
  constructor(
    public name: string,
    public productNo: string,
    public productThumbnail: string | null,
    public unitPrice: number,
    public quantity: number,
    public categoryCode: string,
    public shippingFee: number | null,
    public coupon: string | null,
    public finalPrice: number | null,
  ) {}
}
