package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ArticleNotificationRepository;
import id.co.bca.pakar.be.doc.dto.ArticleNotificationDto;
import id.co.bca.pakar.be.doc.dto.RequestUpdateNotificationDto;
import id.co.bca.pakar.be.doc.dto.SearchNotificationDto;
import id.co.bca.pakar.be.doc.exception.MinValuePageNumberException;
import id.co.bca.pakar.be.doc.model.ArticleNotification;
import id.co.bca.pakar.be.doc.service.ArticleNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleNotificationServiceImpl implements ArticleNotificationService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ArticleNotificationRepository articleNotificationRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleNotificationDto> searchNotification(String username, String token, SearchNotificationDto searchDto) throws Exception {
        try {
            logger.info("search notification process for receiver {}", username);
            Page<ArticleNotification> searchPageResult = null;
            if (searchDto.getPage() == null) {
                searchDto.setPage(0L);
            }

            if (searchDto.getPage() == 0) {
                searchDto.setPage(0L);
            }

            int pageNum = searchDto.getPage().intValue() - 1;
            if (pageNum < 0)
                throw new MinValuePageNumberException("page number smaller than 0");

            Pageable paging = PageRequest.of(searchDto.getPage().intValue() - 1, searchDto.getSize().intValue());
            searchPageResult = articleNotificationRepository.findNotification(username, searchDto.getKeyword(), paging);
            return new ArticleNotificationMapperEntityToDto().mapEntityPageIntoDTOPage(paging, searchPageResult);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long totalReadUnread(String username, boolean read) {
        try {
            logger.info("get total read and unread notification with read status {}", read);
            long count = articleNotificationRepository.countByReceiverAndReadStatus(username, read);
            return count;
        } catch (Exception e) {
            logger.error("exception");
            return 0L;
        }
    }

    @Override
    @Transactional
    public long updateReadNotification(String username, RequestUpdateNotificationDto reqDto) {
        try {
            logger.info("update article notification status to read status");
            if(reqDto.getIsAll())
                return articleNotificationRepository.updateAllReadStatus(username);
            else
                return articleNotificationRepository.updateReadStatus(username, reqDto.getIds());
        } catch (Exception e) {
            logger.error("exception", e);
            return -1;
        }
    }

    private class ArticleNotificationMapperEntityToDto {
        public List<ArticleNotificationDto> mapEntitiesIntoDTOs(List<ArticleNotification> entities) {
            List<ArticleNotificationDto> dtos = new ArrayList<>();
            entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
            return dtos;
        }

        public ArticleNotificationDto mapEntityIntoDTO(ArticleNotification entity) {
            ArticleNotificationDto dto = new ArticleNotificationDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getArticle().getJudulArticle());
            dto.setDesc(entity.getSendNote());
            dto.setDate(entity.getNotifDate());
            dto.setBy(entity.getSender());
            dto.setStatus(entity.getStatus());
            dto.setRead(entity.isRead());
            dto.setType(entity.getDocumentType());
            dto.setRefId(new StringBuffer().append(entity.getArticle().getId()).toString());
            return dto;
        }

        public Page<ArticleNotificationDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<ArticleNotification> source) {
            logger.debug("start mappping article notification to dto ");
            List<ArticleNotificationDto> dtos = mapEntitiesIntoDTOs(source.getContent());
            return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
        }
    }
}
