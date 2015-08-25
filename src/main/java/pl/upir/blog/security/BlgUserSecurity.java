package pl.upir.blog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserRole;

import java.util.*;

/**
 * Created by Vitalii on 01.07.2015.
 */
public class BlgUserSecurity extends BlgUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    public BlgUserSecurity(BlgUser blgUser) {
        if(blgUser!=null){
            this.setUsrId(blgUser.getUsrId());
            this.setUsrLogin(blgUser.getUsrLogin());
            this.setUsrPassword(blgUser.getUsrPassword());
            this.setBlgUserDetail(blgUser.getBlgUserDetail());
            this.setBlgUserRoleSet(blgUser.getBlgUserRoleSet());
        }
    }
    public BlgUserSecurity(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayDeque<GrantedAuthority>();
        Set<BlgDicRole> roles = this.getBlgUserRoleSet();
        if(roles!=null){
            for(BlgDicRole blgDicRole:roles){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(blgDicRole.getRoleName());
                authorities.add(authority);
            }
        }
        return authorities;
    }





    @Override
    public String getPassword() {
        return super.getUsrPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsrLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
