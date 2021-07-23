package id.co.bca.pakar.be.doc.api.validator;

import id.co.bca.pakar.be.doc.dto.ArticleContentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class MultiArticleContentValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(Class<?> clazz) {
        return MultiArticleContentValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<ArticleContentDto> multiDto = (List<ArticleContentDto>) target;

        try {
            int index = 0;
            // validate maximum level
            for (ArticleContentDto contentDto : multiDto) {
                if (contentDto.getLevel() > 5) {
                    errors.rejectValue("level", "maximum.topic.level.reached", new Object[]{5}, "topic title level maximum 5");
                    return;
                }
            }

            for (ArticleContentDto contentDto : multiDto) {
                if (!contentDto.getTopicTitle().isEmpty()) {
                    if (contentDto.getTopicTitle().length() > 150)
                        errors.rejectValue("topicTitle", "maximum.topic.title.reached", new Object[]{150}, "topic title maximum 150 characters");
                    return;
                }
            }
        } catch (Exception e) {
            return;
        }
    }
}
