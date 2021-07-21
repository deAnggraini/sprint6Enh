package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.MultipartArticleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class MultipartArticleValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.article.width-article-image}")
    private String widthImage = "307"; // px

    @Value("${spring.article.height-article-image}")
    private String heightImage = "425"; // px

    @Value("${spring.multipart.file-type}")
    private String fileType;

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartArticleValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<MultipartArticleDto> multiDto = (List<MultipartArticleDto>) target;

        try {
            int index = 0;
            for (MultipartArticleDto dto : multiDto) {
                if (dto.getImage().isEmpty()) {
                    errors.rejectValue("name", "name.required", "name is required");
                    return;
                }
//                if (dto.getName().length() > 50) {
//                    errors.rejectValue("name", "name.maximum.length","maximum length 50 characters");
//                    return;
//                }
//
//                if (dto.getDesc().isEmpty()) {
//                    errors.rejectValue("desc", "description.required","description is required");
//                    return;
//                }
//
//                if (dto.getDesc().length() > 200) {
//                    errors.rejectValue("desc", "description.maximum.length","maximum length description 200 characters");
//                    return;
//                }
//
//                if (dto.getSort().longValue() < 1) {
//                    errors.rejectValue("sort", "sort.minimum","minimum sort value is 1");
//                    return;
//                }
//
//                if (dto.getLevel() < 1) {
//                    errors.rejectValue("level", "level.minimum.invalid","minimum level value 1");
//                    return;
//                }
//
//                if (dto.getLevel() > 4) {
//                    logger.info("level value exceeded");
//                    errors.rejectValue("level", "level.maximum.invalid","maximum value 4");
//                    return;
//                }
//
//                if (dto.getParent() < 0) {
//                    errors.rejectValue("parent", "parent.parent.invalid","minimum parent value is 0");
//                    return;
//                }

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
                            errors.rejectValue("image", "icon.file.type.invalid", "invalid image file type");
                            return;
                        }

//                        if (dto.getImage().getSize() > Long.parseLong(maxFileSize)) {
//                            errors.rejectValue("image", "image.file.size.exceeded","exceeded file size");
//                            return;
//                        }
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }
}
