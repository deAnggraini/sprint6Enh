package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.MyPageDto;
import id.co.bca.pakar.be.doc.dto.SearchMyPageDto;
import org.springframework.data.domain.Page;

public interface ArticleContentService {
    Page<MyPageDto> searchContent(SearchMyPageDto searchDto) throws Exception;
}
