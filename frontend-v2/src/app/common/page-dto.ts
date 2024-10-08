export interface PageDto<T> {
  [x: string]: any;
  items: T[];
  page: number;
  size: number;
  totalItems: number;
  totalPages: number;
}
