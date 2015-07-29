package pl.upir.blog.web.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Vitalii on 02.07.2015.
 */
public class SecurityUserDetail extends BlgUser implements UserDetails {

    public SecurityUserDetail(BlgUser blgUser) {
        if(blgUser!=null){
            this.setUsrId(blgUser.getUsrId());
            this.setUsrLogin(blgUser.getUsrLogin());
            this.setUsrPassword(blgUser.getUsrPassword());
            this.setGetBlgUserDetail(blgUser.getGetBlgUserDetail());
            this.setBlgUserRoleSet(blgUser.getBlgUserRoleSet());
        }
    }

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

    public String getFirstname(){

        return getGetBlgUserDetail().getUsrDetFirstname();
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
