export class PageDto<T> {
  constructor(
    public items: T[],
    public page: number,
    public size: number,
    public totalItems: number,
    public totalPages: number
  ) {}
}
