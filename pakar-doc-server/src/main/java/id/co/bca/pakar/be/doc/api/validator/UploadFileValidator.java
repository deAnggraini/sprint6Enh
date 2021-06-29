package id.co.bca.pakar.be.doc.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UploadFileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UploadFileValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        UploadFileDto dto = (UploadFileDto)target;
//        if(dto.getName().length() < 3)
//            errors.rejectValue("name", "length name minimum 3 characters");
    }
}
