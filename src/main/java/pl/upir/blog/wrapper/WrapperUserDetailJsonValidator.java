package pl.upir.blog.wrapper;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.upir.blog.entity.Gender;
import pl.upir.blog.wrapper.WrapperRegister;

import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vitalii on 25/08/2015.
 */
public class WrapperUserDetailJsonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return WrapperRegister.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        WrapperUserDetailJson wrapperUserDetailJson = (WrapperUserDetailJson)target;

        String gender=wrapperUserDetailJson.getGender();
        Pattern pattern = Pattern.compile("^[F-M].*$");
        Matcher matcher = pattern.matcher(gender);
        if(!matcher.matches()){
            errors.rejectValue("gender error","First letter shoud be in uppercase! ");
        }


    }
}
