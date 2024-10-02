export class CartItem {
  constructor(
    public id: number,
    public name: string,
    public productNo: string,
    public productThumbnail: string | null,
    public unitPrice: number,
    public quantity: number = 1,
  ) {}
}
