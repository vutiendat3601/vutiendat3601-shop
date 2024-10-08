export class ProductRequestCreateDto {
  constructor(
    public sku?: string,
    public slug?: string,
    public name?: string,
    public description?: string,
    public unitPrice: number = 0,
    public unitListedPrice: number = 0,
    public thumbnail?: string | null,
    public categoryId?: number
  ) {}
}
