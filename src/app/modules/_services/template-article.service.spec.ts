import { TestBed } from '@angular/core/testing';

import { TemplateArticleService } from './template-article.service';

describe('TemplateArticleService', () => {
  let service: TemplateArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TemplateArticleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
