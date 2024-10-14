export class CouponDto {
  constructor(
    public code: string,
    public name: string,
    public description: string,
    public discountRatio: number,
    public maxAmount: number,
    public expiredAt: string,
    public categoryCode: string,
    public productNo: string,
    public customerCode: string,
    public type: string,
    public objectType: string
  ) {}
}
