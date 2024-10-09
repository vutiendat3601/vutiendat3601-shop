export class CategoryDto {
  constructor(
    public id?: number,
    public code?: string,
    public slug?: string,
    public name?: string,
    public thumbnail?: string
  ) {}
}
