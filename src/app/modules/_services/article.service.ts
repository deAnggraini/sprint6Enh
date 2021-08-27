import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/utils/_services/api-service.service';
import { environment } from 'src/environments/environment';
import { of, BehaviorSubject, Observable } from 'rxjs';
import * as moment from 'moment';
import { ArticleDTO, ArticleContentDTO } from '../_model/article.dto';
import { toFormData } from './../../utils/_helper/parser';

export interface SearchArticleParam {
  keyword: string,
  page: number,
  limit: number,
  sorting: { column: string, sort: string },
  type: string,
  state: string,
  structureId?: number,
  isLatest?: boolean
}

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  // parameter article form add
  public formParam: any = null;
  public formData: ArticleDTO = null;
  public formAlert: string = null;

  private _base_url = `${environment.apiUrl}/doc`;
  private empty_search: any[] = [
    { state: 0, text: 'Ketentuan Time Loan' },
    { state: 0, text: 'Prosedur Giro' },
    { state: 0, text: 'Open Payment' },
    { state: 0, text: 'Pembukaan Rekening Tahapan' },
    { state: 0, text: 'Flazz' },
  ]

  // share state
  keyword$: BehaviorSubject<string> = new BehaviorSubject<string>('');
  lastKeywords$: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  lastTimeGetKeyword: Date;

  constructor(private apiService: ApiService) { }

  _findNode(id, dataList: ArticleContentDTO[]): ArticleContentDTO {
    let found = dataList.find(d => d.id == id);
    if (!found) {
      dataList.forEach(d => {
        if (found) return;
        found = this._findNode(id, d.children);
      });
    }
    return found;
  }

  lastKeywords() {
    if (!this.lastTimeGetKeyword || moment(this.lastTimeGetKeyword).diff(moment(), 'd') > 0) {
      this.apiService.post(`${this._base_url}/keyword`, {}).subscribe(
        resp => {
          this.lastKeywords$.next(resp);
          this.lastTimeGetKeyword = new Date();
        }
      );
    }
  }

  suggestion(keyword) {
    if (keyword === "") {
      return of(this.empty_search);
    }
    return this.apiService.post(`${this._base_url}/node-suggestion`, { keyword });
  }

  generate(params: any) {
    return this.apiService.post(`${this._base_url}/generateArticle`, params);
  }

  // search my pages
  searchMyPages(keyword: string, state: string = 'DRAFT', type: string = 'ALL', page: number = 1,
    sorting: { column: string, sort: string }, limit: number = 10) {
    const params = { keyword, state, type, page, limit, sorting };
    return this.apiService.post(`${this._base_url}/searchMyPages`, params);
  }

  // search contents
  searchContents(keyword: string, type: string = 'ALL', page: number = 1,
    sorting: { column: string, sort: string }, limit: number = 10) {
    const params = { keyword, type, page, limit, sorting };
    return this.apiService.post(`${this._base_url}/searchContent`, params);
  }

  // search all data [article|faq|pdf]
  search(params: SearchArticleParam = null): Observable<any> {
    if (params) {
      if (params.structureId)
        params.structureId = parseInt(params.structureId.toString()); // fix bug : kadang2 kirim dengan petik dua di json nya
      return this.apiService.post(`${this._base_url}/searchArticle`, params);
    } else {
      return of(false);
    }
  }

  // search only article
  searchArticle(keyword: string, exclude: number = 0, page: number = 1, limit: number = 10) {
    const params = { keyword, exclude, page, limit };
    return this.apiService.post(`${this._base_url}/searchRelatedArticle`, params);
  }

  suggestionArticle(keyword: string, structureId: number, exclude: number[] = [], page: number = 1, limit: number = 10) {
    const params = { keyword, structureId, exclude, page, limit };
    return this.apiService.post(`${this._base_url}/suggestionArticle`, params);
  }

  news() {
    return this.apiService.post(`${this._base_url}/news`, {});
  }

  recommendation() {
    return this.apiService.post(`${this._base_url}/recommendation`, {});
  }

  popular() {
    return this.apiService.post(`${this._base_url}/popular`, {});
  }

  checkUniq(title: string, exclude: number = 0) {
    return this.apiService.post(`${this._base_url}/checkUnique`, { title, exclude }, this.apiService.getHeaders(true), false);
  }

  getById(id: any, isEdit: boolean) {
    const params = { id: parseInt(id), isEdit: isEdit ? true : false }; // convert isEdit to boolean, js mabok
    return this.apiService.post(`${this._base_url}/getArticle`, params);
  }

  getContentId() {
    return this.apiService.get(`${this._base_url}/getContentId`);
  }

  saveContentLevel1(params: ArticleContentDTO) {
    return this.apiService.post(`${this._base_url}/createContentLevel1`, params);
  }

  saveContent(params: any) {
    return this.apiService.post(`${this._base_url}/saveContent`, params);
  }

  deleteContent(id: number) {
    return this.apiService.post(`${this._base_url}/deleteContent`, { id }, this.apiService.getHeaders(), false);
  }

  cancelArticle(id: number) {
    return this.apiService.post(`${this._base_url}/cancelArticle`, { id });
  }

  deleteArticle(body) {
    return this.apiService.post(`${this._base_url}/deletelArticle`, body);
  }

  cancelEditArticle(body) {
    return this.apiService.post(`${this._base_url}/cancelEditArticle`, body);
  }

  cancelSend(id: number, receiver: string) {
    return this.apiService.post(`${this._base_url}/cancelSendArticle`, { id, receiver });
  }

  private parseToArray(content: ArticleContentDTO, index: number): ArticleContentDTO[] {
    let result: ArticleContentDTO[] = [];
    if (content) {
      const _c: ArticleContentDTO = JSON.parse(JSON.stringify(Object.assign({}, content, { children: [] })));
      delete _c.expanded;
      delete _c.isEdit;
      delete _c.no;
      delete _c.listParent;
      _c.sort = index + 1;

      result.push(_c);
      content.children.forEach((d, i) => {
        result = result.concat(this.parseToArray(d, i));
      })
    }
    return result;
  }
  private parseToSingleArray(contents: ArticleContentDTO[]): ArticleContentDTO[] {
    const result: ArticleContentDTO[] = [];
    if (contents.length) {
      contents.forEach((item, i) => {
        this.parseToArray(item, i).forEach(d => result.push(d));
      })
    }
    return result;
  }
  public parseToFormObject(data): FormData {
    let formData = new FormData();
    for (let key in data) {
      if (Array.isArray(data[key])) {
        data[key].forEach((obj, index) => {
          let keyList = Object.keys(obj);
          keyList.forEach((keyItem) => {
            let keyName = [key, "[", index, "]", ".", keyItem].join("");
            formData.append(keyName, obj[keyItem]);
          });
        });
      } else if (typeof data[key] === "object") {
        for (let innerKey in data[key]) {
          formData.append(`${key}.${innerKey}`, data[key][innerKey]);
        }
      } else {
        formData.append(key, data[key]);
      }
    }
    return formData;
  }
  saveArticle(article: ArticleDTO, isHasSend: boolean = false, saveAndSend: any = {}, sendNote: string = '') {
    const _contents = this.parseToSingleArray(article.contents);
    const _dataForm = Object.assign({}, article, { contents: _contents, isHasSend });
    if (isHasSend) {
      _dataForm.sendTo = saveAndSend.sendTo;
      _dataForm.sendNote = saveAndSend.sendNote;
      _dataForm.taskType = 'Approve';
    }
    // const formData = this.parseToFormObject(_dataForm);
    const formData = toFormData(_dataForm);
    const image = formData.get('image');
    if (typeof (image) == "string") formData.delete('image');

    return this.apiService.post(`${this._base_url}/saveArticle`, formData, this.apiService.getHeaders(false));
  }
  checkArticleEditing(id: number) {
    return this.apiService.post(`${this._base_url}/getArticleEditing`, { id });
  }

}
