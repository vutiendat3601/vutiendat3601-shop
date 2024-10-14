export class ProductRequestUpdateDto {
  constructor(
    public sku?: string,
    public name?: string,
    public description?: string,
    public unitPrice: number = 0,
    public thumbnail?: string | null,
    public isActive: boolean = true,
    public categoryCode?: string
  ) {}
}
