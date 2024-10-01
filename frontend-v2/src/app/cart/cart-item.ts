export class CartItem {
  constructor(
    public productNo: string,
    public quantity: number,
    public couponCode: string | null,
    public productThumbnail: string | null,
    public unitPrice: number
  ) {}
}
