package pl.upir.blog.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Component("mD5Encoder")
public class MD5Encoder implements PasswordEncoder {

   /* public String encodePassword(String rawPass, Object salt) throws DataAccessException {
        //logger.debug("Encoding password.");
        return BCrypt.hashpw(rawPass, BCrypt.gensalt());
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
        //logger.debug("Validating password.");
        return BCrypt.checkpw(rawPass, encPass);
    }
*/
    @Override
    public String encode(CharSequence charSequence) {
        return BCrypt.hashpw((String) charSequence, BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return BCrypt.checkpw((String) charSequence, s);
    }

}