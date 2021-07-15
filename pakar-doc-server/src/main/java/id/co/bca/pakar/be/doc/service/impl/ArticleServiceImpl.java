package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.BaseArticleDto;
import id.co.bca.pakar.be.doc.dto.ContentTemplateDto;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.ArticleService;
import id.co.bca.pakar.be.doc.util.TreeContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.article.param-tag:[]}")
    private String paramtTag;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;

    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;

    @Autowired
    private ArticleTemplateContentRepository articleTemplateContentRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Override
    @Transactional
    public Boolean existArticle(String title) {
        try {
            logger.info("verify existence of article title ---> {}", title);
            Boolean exist = articleRepository.existByArticleTitle(title);
            logger.info("title {} {}", title, exist.booleanValue() ? "exist in database" : "not exist in database");
            return exist;
        } catch (Exception e) {
            logger.error("exception", e);
            return Boolean.FALSE;
        }
    }

    /**
     *
     * @param articleDto
     * @return
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public Long generateArticle(BaseArticleDto articleDto) {
        try {
            logger.info("generate article process");
            ArticleTemplateStructure articleTemplateStructure = articleTemplateStructureRepository.findArticleTemplates(articleDto.getTemplateId(), articleDto.getStructureId());
            ArticleTemplate template = articleTemplateStructure.getArticleTemplate();
            Article article = new Article();
            article.setCreatedBy(articleDto.getUsername());
            article.setJudulArticle(articleDto.getJudulArticle());
            article.setArticleTemplate(template.getId());
            article.setArticleUsedBy(articleDto.getUsedBy());
            Structure structure = structureRepository.findStructure(articleDto.getStructureId());
            article.setStructure(structure);
            article.setShortDescription(template.getDescription());

            logger.info("save article");
            article = articleRepository.save(article);

            // save content article
            Iterable<ArticleTemplateContent> templateContents = articleTemplateContentRepository.findByTemplateId(template.getId());
            List<ContentTemplateDto> contentTemplateDtos = new TreeContents().menuTree(mapToList(templateContents));
            for(ContentTemplateDto contentTemplateDto : contentTemplateDtos) {

            }
            return article.getId();
        } catch (Exception e) {
            logger.error("",e);
            return 0L;
        }
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ArticleDto saveArticle(ArticleDto articleDto) {
        try {
            logger.info("save article process");
            return null;
        } catch (Exception e) {
            logger.error("",e);
            return new ArticleDto();
        }
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
