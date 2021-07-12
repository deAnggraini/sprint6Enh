package id.co.bca.pakar.be.doc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import id.co.bca.pakar.be.doc.api.BaseController;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateContentRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateStructureRepository;
import id.co.bca.pakar.be.doc.dto.ArticleTemplateDto;
import id.co.bca.pakar.be.doc.dto.ContentTemplateDto;
import id.co.bca.pakar.be.doc.model.ArticleTemplateContent;
import id.co.bca.pakar.be.doc.model.ArticleTemplateStructure;
import id.co.bca.pakar.be.doc.service.ArticleTemplateService;
import id.co.bca.pakar.be.doc.util.JSONMapperAdapter;
import id.co.bca.pakar.be.doc.util.RestEntity;
import id.co.bca.pakar.be.doc.util.TreeContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ArticleTemplateServiceImp implements ArticleTemplateService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.security.oauth2.server.url}")
    private String uri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;
    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;
    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;

    @Override
    public List<ArticleTemplateDto> findTemplatesByStructureId(Long structureId) {
        List<ArticleTemplateStructure> templates = articleTemplateStructureRepository.findArticleTemplatesByStructureId(structureId);
        List<ArticleTemplateDto> dtoTemplates = new ArrayList<>();
        for(int i = 0; i< templates.size(); i++) {
            ArticleTemplateStructure template = templates.get(i);
            List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getArticleTemplate().getId());
            List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
            ArticleTemplateDto dto = new ArticleTemplateDto();
            dto.setName(template.getArticleTemplate().getTemplateName());
            dto.setDesc(template.getArticleTemplate().getDescription());
            dto.setImage("");
            dto.setThumb("");
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
            headers.add("Authorization", "Bearer " + tokenValue);
            headers.add("X-USERNAME", username);
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
            get all template
             */
            List<ArticleTemplateStructure> templates = articleTemplateStructureRepository.findArticleTemplatesByStructureId(structureId);
            for(ArticleTemplateStructure template : templates) {
                List<ArticleTemplateContent> contents = articleTemplateContentRepository.findByTemplateId(template.getArticleTemplate().getId());
                List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(contents));
                ArticleTemplateDto dto = new ArticleTemplateDto();
                dto.setName(template.getArticleTemplate().getTemplateName());
                dto.setDesc(template.getArticleTemplate().getDescription());
                dto.setImage("");
                dto.setThumb("");
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
            contentDto.setParent(content.getParent());
            contentDto.setTitle(content.getName());
            contentDto.setDesc(content.getDescription());
            listOfContents.add(contentDto);
        }
        return listOfContents;
    }

}
