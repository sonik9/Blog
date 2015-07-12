package pl.upir.blog.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.security.BlgUserSecurity;
import pl.upir.blog.service.BlgUserService;
import pl.upir.blog.web.util.SecurityUserDetail;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Service("blgUserSecurityService")
  //  @Component
@Transactional
public class BlgUserSecurityServiceImpl implements UserDetailsService{

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
