package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.StructureWithFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
            errors.rejectValue("name", "name.required","name is required");
            return;
        }
        if(dto.getName().length() > 50) {
            errors.rejectValue("name", "name.maximum.length","maximum length 50 characters");
            return;
        }
//        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]{1,50}");
//        Matcher matcher = pattern.matcher(dto.getName());
//        if(!matcher.matches()) {
//            errors.rejectValue("name", "name must contain alpha and space only");
//            return;
//        }

        if(dto.getDesc().isEmpty()) {
            errors.rejectValue("desc", "description.required","description is required");
            return;
        }

        if(dto.getDesc().length() > 200) {
            errors.rejectValue("desc", "description.maximum.length","maximum length description 200 characters");
            return;
        }
//        if(!dto.getDesc().matches("^[a-zA-Z\\s]{1,200}")) {
//            errors.rejectValue("desc", "description must contain alpha and space only");
//            return;
//        }

        if(dto.getSort().longValue() < 1) {
            errors.rejectValue("sort", "sort.minimum","minimum sort value is 1");
            return;
        }

        if(dto.getLevel() < 1 || dto.getLevel() > 4) {
            errors.rejectValue("level", "level.minimum.maximum.invalid","minimum level value 1 or maximum value 4");
            return;
        }

        if(dto.getLevel() > 1) {
            if(dto.getParent().longValue() == 0) {
                errors.rejectValue("parent", "level.parent.invalid","invalid parent value cause level > 1");
                return;
            }
        }

        if(dto.getParent() < 0) {
            errors.rejectValue("parent", "minimum.parent.invalid","minimum parent value is 0");
            return;
        }

        if(dto.getId() > 0) {
            if (dto.getParent().longValue() == dto.getId().longValue()) {
                errors.rejectValue("parent", "same.structureid.and.parentid", "structure id dan parent id sama");
                return;
            }
        }

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
                    errors.rejectValue("icon", "icon.file.type.invalid","invalid icon file type");
                    return;
                }

                if (dto.getIcon().getSize() > Long.parseLong(maxFileSize)) {
                    errors.rejectValue("icon", "icon.file.size.exceeded","exceeded file size");
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
                    errors.rejectValue("image", "image.file.type.invalid","invalid image file type");
                    return;
                }

                if (dto.getImage().getSize() > Long.parseLong(maxFileSize)) {
                    errors.rejectValue("image", "image.file.size.exceeded","exceeded file size");
                    return;
                }
            }
        }

        // validate location, location_text if level > 1
        if(dto.getLevel().intValue() > 1) {
            if (dto.getLocation() == null) {
                errors.rejectValue("location", "location.required","location field is required");
                return;
            }

            if (dto.getLocation().isEmpty()) {
                errors.rejectValue("location", "location.required","location field is required");
                return;
            }

            if (dto.getLocation_text() == null) {
                errors.rejectValue("location_text", "location_text.required","location_text field is required");
                return;
            }

            if (dto.getLocation_text().isEmpty()) {
                errors.rejectValue("location_text", "location_text.required","location_text field is required");
                return;
            }
        }
    }
}
