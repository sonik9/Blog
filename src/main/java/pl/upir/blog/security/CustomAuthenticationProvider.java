package pl.upir.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.service.BlgUserService;

/**
 * Created by Vitalii on 12.08.2015.
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    BlgUserService blgUserService;

    @ Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //тут могут быть доп. проверки


        return authentication;
    }

    @ Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Authentication trust(UserDetails user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication trustedAuthentication = new CustomUserAuthentication(user, authentication.getDetails());
        authentication = authenticate(trustedAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
