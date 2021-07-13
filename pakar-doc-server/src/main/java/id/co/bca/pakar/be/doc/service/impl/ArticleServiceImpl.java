package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ArticleRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateRepository;
import id.co.bca.pakar.be.doc.dao.ArticleTemplateStructureRepository;
import id.co.bca.pakar.be.doc.dto.ArticleDto;
import id.co.bca.pakar.be.doc.dto.BaseArticleDto;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleTemplate;
import id.co.bca.pakar.be.doc.model.ArticleTemplateStructure;
import id.co.bca.pakar.be.doc.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTemplateRepository articleTemplateRepository;

    @Autowired
    private ArticleTemplateStructureRepository articleTemplateStructureRepository;

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
            article.setStructure(articleTemplateStructure.getStructure());
            article.setShortDescription(template.getDescription());

            logger.info("save article");
            article = articleRepository.save(article);

            // save content article


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
}
