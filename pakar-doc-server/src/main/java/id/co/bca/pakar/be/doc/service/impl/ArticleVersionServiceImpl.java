package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ArticleVersionRepository;
import id.co.bca.pakar.be.doc.exception.SavingArticleVersionException;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContent;
import id.co.bca.pakar.be.doc.model.ArticleContentVersion;
import id.co.bca.pakar.be.doc.model.ArticleVersion;
import id.co.bca.pakar.be.doc.service.ArticleVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleVersionServiceImpl implements ArticleVersionService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleVersionRepository articleVersionRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class, SavingArticleVersionException.class})
    public ArticleVersion saveArticle(Article article) throws Exception {
        try {
            logger.info("save to article version");
            ArticleVersion av = new ArticleVersionHelper().populateArticleVersion(article);
            List<ArticleContentVersion> articleContentVersions = new ArticleVersionHelper().populateArticleContentVersion(article.getArticleContents(), av);
            av.setArticleContents(articleContentVersions);
            av = articleVersionRepository.save(av);
            if(av == null) {
                logger.error("could not save article version");
                throw new SavingArticleVersionException("could not save article version");
            }
            return av;
        } catch (SavingArticleVersionException e) {
            logger.error("exception",e);
            throw new SavingArticleVersionException("", e);
        } catch (Exception e) {
            logger.error("exception",e);
            throw new Exception(e);
        }
    }

    /**
     * helper class for article version
     */
    private class ArticleVersionHelper {
        ArticleVersion populateArticleVersion(Article article) {
            logger.info("populate article to article version");
            ArticleVersion articleVersion = new ArticleVersion();
            articleVersion.setArticleId(article.getId());
            articleVersion.setCreatedBy(article.getModifyBy());
            articleVersion.setJudulArticle(article.getJudulArticle());
            articleVersion.setNewArticle(articleVersion.getNewArticle());
            articleVersion.setFullNameModifier(article.getFullNameModifier());
            articleVersion.setArticleState(article.getArticleState());
            articleVersion.setArticleUsedBy(article.getArticleUsedBy());
            articleVersion.setShortDescription(article.getShortDescription());
            articleVersion.setUseEmptyTemplate(article.getUseEmptyTemplate());
            articleVersion.setArticleTemplate(article.getArticleTemplate());
            articleVersion.setStructure(article.getStructure().getId());
            articleVersion.setVideoLink(article.getVideoLink());

            return articleVersion;
        }

        /**
         * populate list of article content
         * @param contents
         * @return
         */
        List<ArticleContentVersion> populateArticleContentVersion(List<ArticleContent> contents, ArticleVersion articleVersion) {
            logger.info("populate article content to article content version");
            List<ArticleContentVersion> articleContentVersions = new ArrayList<>();
            contents.forEach(e->  {
                ArticleContentVersion articleContentVersion = new ArticleContentVersion();
                articleContentVersion.setId(e.getId());
                articleContentVersion.setArticleVersion(articleVersion);
                articleContentVersion.setCreatedBy(e.getCreatedBy());
                articleContentVersion.setCreatedDate(e.getCreatedDate());
                articleContentVersion.setModifyBy(e.getModifyBy());
                articleContentVersion.setModifyDate(e.getModifyDate());
                articleContentVersion.setLevel(e.getLevel());
                articleContentVersion.setSort(e.getSort());
                articleContentVersion.setParent(e.getParent());
                articleContentVersion.setName(e.getName());
                articleContentVersion.setDescription(e.getDescription());
                articleContentVersion.setTopicCaption(e.getTopicCaption());
                articleContentVersion.setTopicContent(e.getTopicContent());

                articleContentVersions.add(articleContentVersion);
            });

            return articleContentVersions;
        }
    }
}
