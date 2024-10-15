export class VerificationDto {
  constructor(
    public code: string,
    public expiredAt: string,
    public isDisabled: boolean,
    public type: string
  ) {}
}
