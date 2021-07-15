package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiClient;
import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.ArticleTemplateDto;
import id.co.bca.pakar.be.doc.dto.ContentTemplateDto;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.ArticleTemplateService;
import id.co.bca.pakar.be.doc.util.TreeContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTemplateServiceImp implements ArticleTemplateService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.article.param-tag:[]}")
    private String paramtTag;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;
    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;
    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;
    @Autowired
    private ArticleTemplateThumbnailRepository articleTemplateThumbnailRepository;
    @Autowired
    private ArticleTemplateImageRepository articleTemplateImageRepository;
    @Autowired
    private ApiClient apiClient;

    @Override
    public List<ArticleTemplateDto> findTemplatesByStructureId(Long structureId) {
        List<ArticleTemplateStructure> templates = articleTemplateStructureRepository.findArticleTemplatesByStructureId(structureId);
        List<ArticleTemplateDto> dtoTemplates = new ArrayList<>();
        for(int i = 0; i< templates.size(); i++) {
            ArticleTemplateStructure template = templates.get(i);
            List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getArticleTemplate().getId());
            List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
            ArticleTemplateDto dto = new ArticleTemplateDto();
            dto.setId(template.getId());
            dto.setName(template.getArticleTemplate().getTemplateName());
            dto.setDesc(template.getArticleTemplate().getDescription());
            ArticleTemplateImage articleTemplateImage = articleTemplateImageRepository.findArticleTemplatesImage(template.getId());
            if(articleTemplateImage != null) {
                dto.setImage(articleTemplateImage.getImages().getUri());
            }
            ArticleTemplateThumbnail articleTemplateThumbnail = articleTemplateThumbnailRepository.findArticleTemplatesThumbnail(template.getId());
            if(articleTemplateThumbnail != null) {
                dto.setThumb(articleTemplateThumbnail.getImages().getUri());
            }
            dto.setContent(contentTemplateDtos);
            dtoTemplates.add(dto);
        }
        return dtoTemplates;
    }

    /**
     *
     * @param tokenValue
     * @param structureId
     * @param username
     * @return
     */
    @Override
    public List<ArticleTemplateDto> findTemplatesByStructureId(String tokenValue, Long structureId, String username) {
        List<ArticleTemplateDto> dtoTemplates = new ArrayList<>();
        try {
            String role = apiClient.getRoles(username, tokenValue);
            logger.debug("roles from from username {} ---> {}", username, role);

            /*
            get all article template base on structure id
             */
            List<ArticleTemplate> templates = articleTemplateRepository.findArticleTemplates(structureId, role);
            for(ArticleTemplate template : templates) {
                List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getId());
                List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
                ArticleTemplateDto dto = new ArticleTemplateDto();
                dto.setId(template.getId());
                dto.setName(template.getTemplateName());
                dto.setDesc(template.getDescription());
                ArticleTemplateImage articleTemplateImage = articleTemplateImageRepository.findArticleTemplatesImage(template.getId());
                if(articleTemplateImage != null) {
                    dto.setImage(articleTemplateImage.getImages().getUri());
                }
                ArticleTemplateThumbnail articleTemplateThumbnail = articleTemplateThumbnailRepository.findArticleTemplatesThumbnail(template.getId());
                if(articleTemplateThumbnail != null) {
                    dto.setThumb(articleTemplateThumbnail.getImages().getUri());
                }
                dto.setContent(contentTemplateDtos);
                dtoTemplates.add(dto);
            }
        } catch (RestClientException e) {
            logger.error("exception ", e);
        }
        return dtoTemplates;
    }

    public List<ArticleTemplateDto> findTemplates(String tokenValue, String username) {
        List<ArticleTemplateDto> dtoTemplates = new ArrayList<>();
        try {
            String role = apiClient.getRoles(username, tokenValue);
            logger.debug("roles from from username {} ---> {}", username, role);

            /*
            get all article template base on structure id
             */
            logger.info("get article template by role");
            List<ArticleTemplate> templates = articleTemplateRepository.findArticleTemplates(role);
            for(ArticleTemplate template : templates) {
                List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getId());
                List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
                ArticleTemplateDto dto = new ArticleTemplateDto();
                dto.setId(template.getId());
                dto.setName(template.getTemplateName());
                dto.setDesc(template.getDescription());
                ArticleTemplateImage articleTemplateImage = articleTemplateImageRepository.findArticleTemplatesImage(template.getId());
                if(articleTemplateImage != null) {
                    dto.setImage(articleTemplateImage.getImages().getUri());
                }
                ArticleTemplateThumbnail articleTemplateThumbnail = articleTemplateThumbnailRepository.findArticleTemplatesThumbnail(template.getId());
                if(articleTemplateThumbnail != null) {
                    dto.setThumb(articleTemplateThumbnail.getImages().getUri());
                }
                dto.setContent(contentTemplateDtos);
                dtoTemplates.add(dto);
            }
        } catch (RestClientException e) {
            logger.error("exception ", e);
        }
        return dtoTemplates;
    }

    private List<ContentTemplateDto> mapToList(Iterable<ArticleTemplateContent> iterable) {
        List<ContentTemplateDto> listOfContents = new ArrayList<>();

        for (ArticleTemplateContent content : iterable) {
            ContentTemplateDto contentDto = new ContentTemplateDto();
            contentDto.setId(content.getId());
            contentDto.setLevel(content.getLevel());
            contentDto.setOrder(content.getSort());
            contentDto.setTitle(content.getName());
            contentDto.setDesc(content.getDescription());
            contentDto.setParams(getParams(content.getName()));
            contentDto.setParent(content.getParent());
            listOfContents.add(contentDto);
        }
        return listOfContents;
    }

    private List<String> getParams(String text) {
        logger.debug("split param tag {}", paramtTag);
        String[] tags = paramtTag.split("\\|");
        List<String> params = new ArrayList<>();
        for(int i = 0; i < tags.length; i++) {
            String tagEl = tags[i];
            Character openTag = tagEl.charAt(0);
            Character closeTag = tagEl.charAt(1);

            boolean startExtract = false;
            String param = "";
            for(int j = 0 ; j < text.length(); j++) {
                if(text.charAt(j) == closeTag) {
                    startExtract = false;
                    params.add(param.trim());
                }

                if(startExtract) {
                    param = param + text.charAt(j);
                }

                if(text.charAt(j) == openTag) {
                    startExtract = true;
                }
            }
        }
        return params;
    }
}
