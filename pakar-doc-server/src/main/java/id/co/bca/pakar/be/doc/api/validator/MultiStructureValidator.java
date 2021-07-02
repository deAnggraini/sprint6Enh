package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.MultiStructureDto;
import id.co.bca.pakar.be.doc.dto.StructureWithFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MultiStructureValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.multipart.max-size}")
    private String maxFileSize = "2000000";

    @Value("${spring.multipart.file-type}")
    private String fileType;

    @Override
    public boolean supports(Class<?> clazz) {
        return MultiStructureValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultiStructureDto multiDto = (MultiStructureDto) target;

        int index = 0;
        for (StructureWithFileDto structure : multiDto.getStructureWithFileDtoList()) {
            if (structure.getName().isEmpty()) {
                errors.rejectValue("name", "name is required");
                return;
            }
            if (structure.getName().length() > 50) {
                errors.rejectValue("name", "maximum length 50 characters");
                return;
            }

            if (structure.getDesc().isEmpty()) {
                errors.rejectValue("desc", "description is required");
                return;
            }

            if (structure.getDesc().length() > 200) {
                errors.rejectValue("desc", "maximum length description 200 characters");
                return;
            }

            if (structure.getSort().longValue() < 1) {
                errors.rejectValue("sort", "minimum sort value is 1");
                return;
            }

            if (structure.getLevel() < 1 || structure.getLevel() > 4) {
                errors.rejectValue("level", "minimum level value 1 and maximum value 4");
                return;
            }

            if (structure.getParent() < 0) {
                errors.rejectValue("parent", "minimum parent value is 0");
                return;
            }

            boolean validFileType = false;
            if (structure.getIcon() != null) {
                if (!structure.getIcon().isEmpty()) {
                    String[] fileTypes = fileType.split("\\,");
                    for (String str : fileTypes) {
                        if (structure.getIcon().getContentType().equals(str)) {
                            validFileType = true;
                            break;
                        }
                    }

                    if (!validFileType) {
                        errors.rejectValue("icon", "invalid icon file type");
                        return;
                    }

                    if (structure.getIcon().getSize() > Long.parseLong(maxFileSize)) {
                        errors.rejectValue("icon", "exceeded file size");
                        return;
                    }
                }
            }

            validFileType = false;
            if (structure.getImage() != null) {
                if (!structure.getImage().isEmpty()) {
                    String[] fileTypes = fileType.split("\\,");
                    for (String str : fileTypes) {
                        if (structure.getImage().getContentType().equals(str)) {
                            validFileType = true;
                            break;
                        }
                    }

                    if (!validFileType) {
                        errors.rejectValue("image", "invalid image file type");
                        return;
                    }

                    if (structure.getImage().getSize() > Long.parseLong(maxFileSize)) {
                        errors.rejectValue("image", "exceeded file size");
                        return;
                    }
                }
            }
            // validate location, location_text if level > 1
            if (structure.getLevel().intValue() > 1) {
                if (structure.getLocation() == null) {
                    errors.rejectValue("location", "location field is required");
                    return;
                }

                if (structure.getLocation().isEmpty()) {
                    errors.rejectValue("location", "location field is required");
                    return;
                }

                if (structure.getLocation_text() == null) {
                    errors.rejectValue("location_text", "location_text field is required");
                    return;
                }

                if (structure.getLocation_text().isEmpty()) {
                    errors.rejectValue("location_text", "location_text field is required");
                    return;
                }
            }
        }
    }
}
