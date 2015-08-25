package pl.upir.blog.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.security.BlgUserSecurity;
import pl.upir.blog.security.CustomUserAuthentication;
import pl.upir.blog.service.BlgUserService;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Service("blgUserSecurityService")
  //  @Component
@Transactional
public class BlgUserSecurityServiceImpl implements UserDetailsService{



    @Autowired
    BlgUserService blgUserService;

    @Override
    public UserDetails loadUserByUsername(String usrLogin) throws UsernameNotFoundException {
        BlgUser blgUser = blgUserService.findByUsrLogin(usrLogin);
        if (blgUser == null) {
            throw new UsernameNotFoundException("No user found with username: "+ usrLogin);
        }

        return new BlgUserSecurity(blgUser);
    }

    public Authentication trust(UserDetails user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication trustedAuthentication = new CustomUserAuthentication(user, authentication.getDetails());
        authentication =trustedAuthentication;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

}
