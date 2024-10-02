export class CategoryDto {
  constructor(
    public code: string,
    public slug: string,
    public name: string,
    public thumbnail: string | null
  ) {}
}
