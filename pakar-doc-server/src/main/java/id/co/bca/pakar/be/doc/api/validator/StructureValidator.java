package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.StructureWithFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StructureValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.multipart.max-size}")
    private String maxFileSize = "2000000";

    @Value("${spring.multipart.file-type}")
    private String fileType;

    @Override
    public boolean supports(Class<?> clazz) {
        return StructureValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StructureWithFileDto dto = (StructureWithFileDto)target;

        if(dto.getName().isEmpty()) {
            errors.rejectValue("name", "name is required");
            return;
        }
        if(dto.getName().length() > 50) {
            errors.rejectValue("name", "maximum length 50 characters");
            return;
        }
//        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]{1,50}");
//        Matcher matcher = pattern.matcher(dto.getName());
//        if(!matcher.matches()) {
//            errors.rejectValue("name", "name must contain alpha and space only");
//            return;
//        }

        if(dto.getDesc().isEmpty()) {
            errors.rejectValue("desc", "description is required");
            return;
        }

        if(dto.getDesc().length() > 200) {
            errors.rejectValue("desc", "maximum length description 200 characters");
            return;
        }
//        if(!dto.getDesc().matches("^[a-zA-Z\\s]{1,200}")) {
//            errors.rejectValue("desc", "description must contain alpha and space only");
//            return;
//        }

        if(dto.getSort().longValue() < 1) {
            errors.rejectValue("sort", "minimum sort value is 1");
            return;
        }

        if(dto.getLevel() < 1) {
            errors.rejectValue("level", "minimum level value is 1");
            return;
        }

        if(dto.getLevel() > 1) {
            if(dto.getParent().longValue() == 0) {
                errors.rejectValue("parent", "invalid parent value cause level > 1");
                return;
            }
        }

        if(dto.getParent() < 0) {
            errors.rejectValue("parent", "minimum parent value is 0");
            return;
        }

//        logger.debug("validate level {}", dto.getLevel());
//        if(dto.getLevel().longValue() > 1) {
//            if(!dto.getIcon().isEmpty())  {
//                errors.rejectValue("level", "request icon invalid cause level value > 1");
//                return;
//            }
//        }

        boolean validFileType = false;
        logger.debug("icon value {}", dto.getIcon());
        if(dto.getIcon() != null) {
            if (!dto.getIcon().isEmpty()) {
                String[] fileTypes = fileType.split("\\,");
                for (String str : fileTypes) {
                    if (dto.getIcon().getContentType().equals(str)) {
                        validFileType = true;
                        break;
                    }
                }

                if (!validFileType) {
                    errors.rejectValue("icon", "invalid icon file type");
                    return;
                }

                if (dto.getIcon().getSize() > Long.parseLong(maxFileSize)) {
                    errors.rejectValue("icon", "exceeded file size");
                    return;
                }
            }
        }

        validFileType = false;
        logger.debug("image value {}", dto.getImage());
        if(dto.getImage() != null) {
            if (!dto.getImage().isEmpty()) {
                String[] fileTypes = fileType.split("\\,");
                for (String str : fileTypes) {
                    if (dto.getImage().getContentType().equals(str)) {
                        validFileType = true;
                        break;
                    }
                }

                if (!validFileType) {
                    errors.rejectValue("image", "invalid image file type");
                    return;
                }

                if (dto.getImage().getSize() > Long.parseLong(maxFileSize)) {
                    errors.rejectValue("image", "exceeded file size");
                    return;
                }
            }
        }
    }
}
