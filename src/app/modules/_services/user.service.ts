import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { ToastService } from 'src/app/utils/_services/toast.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Option } from 'src/app/utils/_model/option';
import { UserModel } from '../auth';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _base_url = `${environment.apiUrl}/auth`;

  constructor(private api: ApiService) {
  }

  searchUserNotReader(keyword: string) {
    return this.api.post(`${this._base_url}/searchUserNotReader`, { keyword });
  }

  searchUserApprover(body: { keyword: string, role: string, username: string }) {
    return this.api.post(`${this._base_url}/getUsersByRole`, body);
  }

  usersToOptions(users: UserModel[]): Option[] {
    const result: Option[] = [];
    if (users && users.length) {
      users.forEach(d => {
        result.push({
          id: d.username,
          text: `${d.fullname}`,
          value: d.email
        });
      })
    }
    return result;
  }
}
