package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleTemplateDto;

import java.util.List;

public interface ArticleTemplateService {
    List<ArticleTemplateDto> findTemplatesByStructureId(Long structureId);
    List<ArticleTemplateDto> findTemplatesByStructureId(String tokenValue, Long structureId, String username);
    List<ArticleTemplateDto> findTemplates(String tokenValue, String username);
}
