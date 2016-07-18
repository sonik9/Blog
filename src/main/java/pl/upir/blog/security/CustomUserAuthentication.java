package pl.upir.blog.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.upir.blog.entity.BlgUser;

import java.util.Collection;

/**
 * Created by Vitalii on 12.08.2015.
 */
public class CustomUserAuthentication extends BlgUser implements Authentication {
    private static final long serialVersionUID = 1L;

    private String name;
    private Object details;
    private UserDetails user;
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserAuthentication(UserDetails user, Object details) {
        this.name = user.getUsername();
        this.details = details;
        this.user = user;
        this.authorities = user.getAuthorities();
        authenticated = true;
    }

    @ Override
    public String getName() {
        return name;
    }
    @ Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @ Override
    public Object getCredentials() {
        return user.getPassword();
    }
    @ Override
    public Object getDetails() {
        return details;
    }
    @ Override
    public Object getPrincipal() {
        return user;
    }
    @ Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    @ Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }
   /* public String getFirstname(){
        return super.getBlgUserDetail().getUsrDetFirstname();
    }
    public String getLastname(){
        return super.getBlgUserDetail().getUsrDetLastname();
    }*/
}
