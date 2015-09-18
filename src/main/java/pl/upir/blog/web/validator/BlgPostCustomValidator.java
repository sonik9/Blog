package pl.upir.blog.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.upir.blog.entity.BlgPost;

/**
 * Created by Vitalii on 18/09/2015.
 */
@Component("blgPostCustomValidator")
public class BlgPostCustomValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BlgPost.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BlgPost blgPost = (BlgPost)target;
        if(blgPost.getBlgDicCategorySet().isEmpty()){
            //errors.reject("Category error","owiennoiweiovnwe");
            errors.rejectValue("blgDicCategorySet", "Category error","validation.pstCategory.NotEmpty.message");
        }
        if(blgPost.getBlgDicTagSet()!=null) {
            if (blgPost.getBlgDicTagSet().isEmpty()) {
                errors.rejectValue("blgDicTagSet", "Tags error", "validation.pstTag.NotEmpty.message");
            }
        }else {
            errors.rejectValue("blgDicTagSet", "Tags error", "validation.pstTag.NotEmpty.message");
        }
    }
}
