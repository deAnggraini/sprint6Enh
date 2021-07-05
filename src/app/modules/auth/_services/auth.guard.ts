import { Injectable } from '@angular/core';
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateChild,
} from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { UserModel } from '../_models/user.model';

export const _ROLES = [
  'ADMIN', 'EDITOR', 'PUBLISHER', 'STAFF', 'VENDOR', 'GUEST'
];

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authService.currentUserValue;
    if (currentUser) {
      return true;
    }
    this.authService.logout(true);
    return false;
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authService.currentUserValue;
    const allowedRoles = route.data.allowedRoles;
    if (this.isAuthorized(allowedRoles, currentUser)) {
      return true;
    } else {
      this.router.navigate(['accessdenied']);
      return false;
    }
  }

  isAuthorized(allowedRoles: string[], currentUser: UserModel): boolean {

    // jika tidak didefinsikan, semua role boleh mengakses
    if (allowedRoles == null || allowedRoles.length === 0) {
      return true;
    }

    const convertRole = currentUser.roles[0];
    const hasil = allowedRoles.includes(convertRole);
    return hasil;
  }
}
