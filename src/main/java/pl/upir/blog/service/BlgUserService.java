package pl.upir.blog.service;

import pl.upir.blog.entity.BlgUser;

import java.util.List;

/**
 * Created by Vitalii on 22.06.2015.
 */
public interface BlgUserService {
    public List<BlgUser> findAll();
    public BlgUser findById(int id);
    public BlgUser save(BlgUser blgUsers);
    //public void updateBlgUsersLogin();
    public List<BlgUser> findByusrLogin(String usrLogin);
    public void delete(BlgUser blgUsers);

}
