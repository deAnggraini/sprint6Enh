package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ArticleContentCloneRepository;
import id.co.bca.pakar.be.doc.model.Article;
import id.co.bca.pakar.be.doc.model.ArticleContent;
import id.co.bca.pakar.be.doc.model.ArticleContentClone;
import id.co.bca.pakar.be.doc.service.ArticleCloneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleCloneServiceImpl implements ArticleCloneService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleContentCloneRepository articleContentCloneRepository;

    /*
    clone main article content into article content clone
    - this condiction occured when user reload article
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean cloneArticleContent(Article article, String username) throws Exception {
        try {
            logger.info("clone article content of article id {} for user {}", article.getId(), username);
            logger.info("populate and save cloning article content");
            List<ArticleContentClone> clones = new ArticleCloneHelper().populateContent(article.getArticleContents(), article);
            clones.forEach(e -> articleContentCloneRepository.save(e));
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("exception ", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleContentClone findById(Long id, String username) throws Exception {
        try {
            logger.debug("find by id {} and username {}", id, username);
            Optional<ArticleContentClone> content = null;
            if (username == null)
                content = articleContentCloneRepository.findById(id);
            else
                content = articleContentCloneRepository.findById(id, username);
            return content.isPresent() ? content.get() : null;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public ArticleContentClone saveContent(ArticleContentClone content) throws Exception {
        try {
            return articleContentCloneRepository.save(content);
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception(e);
        }
    }

    /**
     * helper class for article version
     */
    private class ArticleCloneHelper {
        /**
         * populate list of article content
         *
         * @param contents
         * @return
         */
        List<ArticleContentClone> populateContent(List<ArticleContent> contents, Article clone) {
            logger.info("populate article content to article content clone");
            List<ArticleContentClone> clones_ = new ArrayList<>();
            contents.forEach(e -> {
                ArticleContentClone content_ = new ArticleContentClone();
                content_.setId(e.getId());
                content_.setArticle(clone);
                content_.setCreatedBy(e.getCreatedBy());
                content_.setCreatedDate(e.getCreatedDate());
                content_.setModifyBy(e.getModifyBy());
                content_.setModifyDate(e.getModifyDate());
                content_.setLevel(e.getLevel());
                content_.setSort(e.getSort());
                content_.setParent(e.getParent());
                content_.setName(e.getName());
                content_.setDescription(e.getDescription());
                content_.setTopicCaption(e.getTopicCaption());
                content_.setTopicContent(e.getTopicContent());

                clones_.add(content_);
            });

            return clones_;
        }
    }
}
