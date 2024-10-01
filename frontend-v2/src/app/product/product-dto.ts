export class ProductDto {
  constructor(
    public id: number,
    public productNo: string,
    public sku: string,
    public name: string,
    public description: string,
    public unitPrice: number,
    public unitListedPrice: number,
    public thumbnail: string | null,
    public buyedCount: number,
    public tags: string[],
    public likedCount: number,
    public unitsInStock: number
  ) {}
}
