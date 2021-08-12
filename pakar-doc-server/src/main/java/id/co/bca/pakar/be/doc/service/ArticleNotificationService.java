package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.ArticleNotificationDto;
import id.co.bca.pakar.be.doc.dto.RequestUpdateNotificationDto;
import id.co.bca.pakar.be.doc.dto.SearchNotificationDto;
import org.springframework.data.domain.Page;

public interface ArticleNotificationService {
    Page<ArticleNotificationDto> searchNotification(String username, String token, SearchNotificationDto searchDto) throws Exception;
    Long totalReadUnread(String username, boolean read);
    long updateReadNotification(String username, RequestUpdateNotificationDto reqDto);
}