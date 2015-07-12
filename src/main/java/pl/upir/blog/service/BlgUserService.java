package pl.upir.blog.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.upir.blog.entity.BlgUser;

import java.util.List;

/**
 * Created by Vitalii on 22.06.2015.
 */
public interface BlgUserService {
    //@PreAuthorize("hasRole('ROLE_CLIENT')")

    public List<BlgUser> findAll();
    public BlgUser findById(int id);
    public BlgUser save(BlgUser blgUsers);
    //public void updateBlgUsersLogin();
    public BlgUser findByUsrLogin(String usrLogin);
    public void delete(BlgUser blgUsers);

}
