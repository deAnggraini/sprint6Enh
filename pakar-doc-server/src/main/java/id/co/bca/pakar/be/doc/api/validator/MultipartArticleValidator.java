package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.ArticleContentDto;
import id.co.bca.pakar.be.doc.dto.MultipartArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public class MultipartArticleValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.article.width-article-image}")
    private String widthImage = "307"; // px

    @Value("${spring.article.height-article-image}")
    private String heightImage = "425"; // px

    @Value("${spring.article.file-type}")
    private String fileType;

    @Value("${spring.article.max-size}")
    private String maxFileSize = "700000";

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartArticleValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartArticleDto dto = (MultipartArticleDto) target;

        try {
            if (dto.getTitle().isEmpty()) {
                errors.rejectValue("judulArticle", "judul.article.required", "judul article is required");
                return;
            }

            if (dto.getDesc() != null) {
                if (dto.getDesc().length() > 1000) {
                    errors.rejectValue("shortDescription", "shortDescription.article.maximum.length", "maximum length 1000 characters");
                    return;
                }
            }

            boolean validFileType = false;
            if (dto.getImage() != null) {
                if (!dto.getImage().isEmpty()) {
                    String[] fileTypes = fileType.split("\\,");
                    for (String str : fileTypes) {
                        if (dto.getImage().getContentType().equals(str)) {
                            validFileType = true;
                            break;
                        }
                    }

                    if (!validFileType) {
                        errors.rejectValue("image", "article.image.file.type.invalid", "uploaded file type .png, .jpeg, jpeg");
                        return;
                    }

                    if (dto.getImage().getSize() > Long.parseLong(maxFileSize)) {
                        errors.rejectValue("image", "article.image.file.size.exceeded", "exceeded file size");
                        return;
                    }

                    BufferedImage image;
                    try {
                        image = ImageIO.read(dto.getImage().getInputStream());
                        int width = image.getWidth();
                        int heigth = image.getHeight();
                        if (width < Long.parseLong(widthImage)) {
                            errors.rejectValue("image", "article.image.file.width.invalid", "width image size invalid");
                            return;
                        }

                        if (heigth < Long.parseLong(heightImage)) {
                            errors.rejectValue("image", "article.image.file.height.invalid", "height image size invalid");
                            return;
                        }
                    } catch (IOException e) {
                        return;
                    }
                }
            }

            if (dto.getVideo() != null) {
                String regex = "(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
                if (!dto.getVideo().matches(regex)) {
                    errors.rejectValue("videoLink", "article.videoLink.url.invalid", "video link url invalid");
                    return;
                }
            }

            // validate content
            for (ArticleContentDto contentDto : dto.getContents()) {
                int index = 0;

                if (contentDto.getLevel() > 5) {
                    errors.rejectValue("level", "maximum.topic.level.reached", new Object[]{5}, "topic title level maximum 5");
                    return;
                }

                if(dto.getIsHasSend()) {
                    if(contentDto.getTopicTitle().isEmpty()) {
                        errors.rejectValue("topicTitle", "topic.title.required",  "Judul topik wajib diisi");
                        return;
                    }

                    if(contentDto.getTopicContent().isEmpty()) {
                        errors.rejectValue("topicContent", "topic.content.required",  "Isi topik wajib diisi");
                        return;
                    }
                }

                if (!contentDto.getTopicTitle().isEmpty()) {
                    if (contentDto.getTopicTitle().length() > 150)
                        errors.rejectValue("topicTitle", "maximum.topic.title.reached", new Object[]{150}, "topic title maximum 150 characters");
                    return;
                }

                if(contentDto.getSort() < 1) {
                    errors.rejectValue("sort", "sort.minimum", "minimum sort value 1");
                    return;
                }
            }
        } catch (Exception e) {
            return;
        }
    }
}
