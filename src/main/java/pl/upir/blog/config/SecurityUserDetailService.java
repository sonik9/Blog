package pl.upir.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.util.SecurityUserDetail;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Component("securityUserDetailService")
public class SecurityUserDetailService implements UserDetailsService{

    /*public SecurityUserDetailService() {
    }*/

    @Autowired
    BlgUserService blgUserService;

    @Override
    public UserDetails loadUserByUsername(String usrLogin) throws UsernameNotFoundException {
        BlgUser blgUser = blgUserService.findByUsrLogin(usrLogin);
        if (blgUser == null) {
            throw new UsernameNotFoundException("No user found with username: "+ usrLogin);
        }

        return new SecurityUserDetail(blgUser);
    }

}
