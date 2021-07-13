package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateContentRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateStructureRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateThumbnailRepository;
import id.co.bca.pakar.be.doc.dto.ArticleTemplateDto;
import id.co.bca.pakar.be.doc.dto.ContentTemplateDto;
import id.co.bca.pakar.be.doc.model.ArticleTemplateContent;
import id.co.bca.pakar.be.doc.model.ArticleTemplateStructure;
import id.co.bca.pakar.be.doc.model.ArticleTemplateThumbnail;
import id.co.bca.pakar.be.doc.service.ArticleTemplateService;
import id.co.bca.pakar.be.doc.util.TreeContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ArticleTemplateServiceImp implements ArticleTemplateService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.security.oauth2.server.url}")
    private String uri;

    @Value("${spring.article.param-tag:[]}")
    private String paramtTag;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;
    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;
    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;
    @Autowired
    private ArticleTemplateThumbnailRepository articleTemplateThumbnailRepository;

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
            dto.setImage("");
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
            String role = null;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add(Constant.Headers.AUTHORIZATION, Constant.Headers.BEARER + tokenValue);
            headers.add(Constant.Headers.X_USERNAME, username);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            String url_api = uri + "/api/auth/getRoles";
//            RestEntity.RestResponse response = null;
            logger.debug("get roles from api {}", url_api);
//            response = restTemplate.exchange(url_api, HttpMethod.POST, request, RestEntity.RestResponse.class);
            Map map = restTemplate.exchange(url_api, HttpMethod.POST, request, Map.class).getBody();
            logger.debug("response body {}", map);
            if(map.containsKey("status")) {
                Map apiStatus = (Map)map.get("status");
                String code = (String)apiStatus.get("code");
                if(!code.equals("00")) {
                    throw new RestClientException("");
                }
                logger.debug("get role from response data {}",map.get("data"));
                List jsonRoles =  (List)map.get("data");
                logger.debug("get role from response data {}",jsonRoles);
                role = (String)jsonRoles.get(0);
            }
            logger.debug("roles from from username {} ---> {}", username, role);

            /*
            get all article template base on structure id
             */
            List<ArticleTemplateStructure> templates = articleTemplateStructureRepository.findArticleTemplatesByStructureId(structureId);
            for(ArticleTemplateStructure template : templates) {
                List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getArticleTemplate().getId());
                List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
                ArticleTemplateDto dto = new ArticleTemplateDto();
                dto.setId(template.getId());
                dto.setName(template.getArticleTemplate().getTemplateName());
                dto.setDesc(template.getArticleTemplate().getDescription());
                dto.setImage("");
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
