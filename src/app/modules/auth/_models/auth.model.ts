export class AuthModel {
  id: number;
  username: string;
  authToken: string;
  refreshToken: string;
  expiresIn: Date;

  setAuth(auth: any) {
    this.authToken = auth.authToken;
    this.refreshToken = auth.refreshToken;
    this.expiresIn = auth.expiresIn;
  }
}
