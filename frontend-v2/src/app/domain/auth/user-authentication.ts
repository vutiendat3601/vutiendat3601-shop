export class UserAuthentication {
  constructor(
    public id: number,
    public username: string,
    public name: string,
    public isVerified: boolean,
    public customerCode: string
  ) {}
}
