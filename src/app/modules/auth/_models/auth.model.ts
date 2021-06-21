export class AuthModel {
  id: number;
  username: string;
  authToken: string;
  refreshToken: string;
  expiresIn: number;
  autoLogout: Date;
  remember: boolean;

  setAuth(auth: any) {
    this.authToken = auth.authToken;
    this.refreshToken = auth.refreshToken;
    this.expiresIn = auth.expiresIn;
    this.remember = auth.remember;
  }
}
