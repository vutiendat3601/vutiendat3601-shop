export class ProductDto {
  constructor(
    public id?: number,
    public productNo?: string,
    public sku?: string,
    public name?: string,
    public description?: string,
    public unitPrice: number = 0,
    public unitListedPrice: number = 0,
    public thumbnail?: string | null,
    public buyedCount: number = 10,
    public tags?: string[],
    public likedCount: number = 10,
    public unitsInStock?: number
  ) {}
}
