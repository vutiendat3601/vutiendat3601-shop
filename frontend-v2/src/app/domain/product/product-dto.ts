export class ProductDto {
  constructor(
    public productNo: string,
    public sku: string,
    public slug: string,
    public name: string,
    public description: string,
    public unitPrice: number = 0,
    public unitListedPrice: number = 0,
    public thumbnail: string | null,
    public buyedCount: number = 0,
    public tags: string[],
    public likedCount: number = 0,
    public unitsInStock: number,
    public categoryCode: string,
    public isActive: boolean = true
  ) {}
}
