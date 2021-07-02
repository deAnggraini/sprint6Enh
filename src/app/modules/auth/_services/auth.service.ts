import { Injectable, OnDestroy } from '@angular/core';
import { Observable, BehaviorSubject, of, Subscription } from 'rxjs';
import { map, catchError, switchMap, finalize, concatMap } from 'rxjs/operators';
import { UserModel } from '../_models/user.model';
import { AuthModel } from '../_models/auth.model';
import { AuthHTTPService } from './auth-http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import * as moment from 'moment';
import { ToastService } from 'src/app/utils/_services/toast.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService implements OnDestroy {
  // private fields
  private unsubscribe: Subscription[] = []; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/
  private authLocalStorageToken = `${environment.appVersion}-${environment.USERDATA_KEY}`;
  private oauth_url = `${environment.apiUrl}/auth`;

  // public fields
  currentUser$: Observable<UserModel>;
  isLoading$: Observable<boolean>;
  currentUserSubject: BehaviorSubject<UserModel>;
  isLoadingSubject: BehaviorSubject<boolean>;
  logoutWorker: any = null;


  get currentUserValue(): UserModel {
    return this.currentUserSubject.value;
  }

  set currentUserValue(user: UserModel) {
    this.currentUserSubject.next(user);
  }

  constructor(
    private authHttpService: AuthHTTPService,
    private router: Router,
    private apiService: ApiService,
    private toast: ToastService
  ) {
    this.isLoadingSubject = new BehaviorSubject<boolean>(false);
    this.currentUserSubject = new BehaviorSubject<UserModel>(undefined);
    this.currentUser$ = this.currentUserSubject.asObservable();
    this.isLoading$ = this.isLoadingSubject.asObservable();
    const subscr = this.getUserByToken().subscribe();
    this.unsubscribe.push(subscr);
  }

  refreshToken(auth: AuthModel) {
    this.logoutWorker = null;
    const params = { refreshToken: auth.refreshToken };
    this.apiService.post(`${this.oauth_url}/refreshToken`, params).subscribe(
      resp => {
        const { authToken, refreshToken, expiresIn } = resp;
        auth.authToken = authToken;
        auth.refreshToken = refreshToken;
        auth.expiresIn = expiresIn;
        auth.autoLogout = moment().add(expiresIn, 's').toDate();
        this.setAuthFromLocalStorage(auth);
        this.setWorker(auth, expiresIn * 1000);
      }
    );
  }

  setWorker(auth: AuthModel, duration: number) {
    if (this.logoutWorker === null) {
      console.log('set worker ', duration / 1000, 'seconds');
      this.logoutWorker = setTimeout(() => {
        if (auth.remember == true) {
          console.log('doing auto refresh token');
          this.refreshToken(auth);
        } else {
          console.log('doing auto logout');
          this.logout(true);
        }
      }, duration);
    }
  }

  // public methods
  login(username: string, password: string, remember: boolean): Observable<UserModel> {
    this.isLoadingSubject.next(true);
    const params = { username, password, remember };
    return this.apiService.post(`${this.oauth_url}/login`, params, this.apiService.getHeaders(), false)
      .pipe(
        catchError((err) => {
          throw err;
          return of(err);
        }),
        map((auth: AuthModel) => {
          if (auth) {
            this.toast.clear();
            const { expiresIn } = auth;
            const autoLogout = moment().add(expiresIn, 's').toDate();
            auth = Object.assign({}, auth, { autoLogout, remember });
            this.setWorker(auth, auth.expiresIn * 1000);
            this.setAuthFromLocalStorage(auth);
          }
          return auth;
        }),
        switchMap(() => this.getUserByToken()),
        finalize(() => this.isLoadingSubject.next(false))
      );
  }

  logout(isReload = false) {
    clearTimeout(this.logoutWorker);
    this.logoutWorker = null;
    // };
    this.apiService.post(`${this.oauth_url}/logout`, {}, this.apiService.getHeaders(), false).subscribe(
      resp => {
        if (resp) {
          localStorage.removeItem(this.authLocalStorageToken);
          // this.router.navigate(['/auth/login'], {
          //   queryParams: {},
          // });
          if (isReload) location.replace('/auth/login');
        }
      }
      // error => {
      //   localStorage.removeItem(this.authLocalStorageToken);
      //   this.router.navigate(['/auth/login'], {
      //     queryParams: {},
      //   });
      //   location.reload();
      // }
    );
  }

  getUserByToken(): Observable<UserModel> {
    const auth = this.getAuthFromLocalStorage();
    if (!auth || !auth.authToken) {
      return of(undefined);
    }

    // check autologout
    if (auth.autoLogout && this.logoutWorker == null) {
      const duration = moment(auth.autoLogout);
      this.setWorker(auth, duration.diff(moment()));
    }

    this.isLoadingSubject.next(true);
    const { authToken, username } = auth;
    const params = { authToken, username };
    return this.apiService.post(`${this.oauth_url}/getUser`, params).pipe(
      catchError((err) => {
        this.logout(true);
        return of(undefined);
      }),
      map((user: UserModel) => {
        if (user) {
          this.currentUserSubject = new BehaviorSubject<UserModel>(user);
          // this.setWorker();
        } else {
          this.logout(true);
        }
        return user;
      }),
      finalize(() => this.isLoadingSubject.next(false))
    );
  }

  // need create new user then login
  registration(user: UserModel): Observable<any> {
    this.isLoadingSubject.next(true);
    return this.authHttpService.createUser(user).pipe(
      map(() => {
        this.isLoadingSubject.next(false);
      }),
      switchMap(() => this.login(user.email, user.password, true)),
      catchError((err) => {
        console.error('err', err);
        return of(undefined);
      }),
      finalize(() => this.isLoadingSubject.next(false))
    );
  }

  forgotPassword(email: string): Observable<boolean> {
    this.isLoadingSubject.next(true);
    return this.authHttpService
      .forgotPassword(email)
      .pipe(finalize(() => this.isLoadingSubject.next(false)));
  }

  // private methods
  private setAuthFromLocalStorage(auth: AuthModel): boolean {
    // store auth authToken/refreshToken/epiresIn in local storage to keep user logged in between page refreshes
    if (auth && auth.authToken) {
      localStorage.setItem(this.authLocalStorageToken, JSON.stringify(auth));
      return true;
    }
    return false;
  }

  private getAuthFromLocalStorage(): AuthModel {
    try {
      const authData = JSON.parse(
        localStorage.getItem(this.authLocalStorageToken)
      );
      return authData;
    } catch (error) {
      console.error(error);
      return undefined;
    }
  }

  ngOnDestroy() {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }
}
