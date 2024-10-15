export class AddressDetailDto {
  constructor(
    public code: string,
    public provinceId: number,
    public provinceName: string,
    public districtId: number,
    public districtName: string,
    public wardId: number,
    public wardName: string,
    public street: string,
    public customerCode: string
  ) {}
}
