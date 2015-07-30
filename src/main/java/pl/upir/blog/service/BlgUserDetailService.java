package pl.upir.blog.service;

import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserDetail;

import java.util.List;

/**
 * Created by Vitalii on 01.07.2015.
 */
public interface BlgUserDetailService {
    public BlgUserDetail findById(int id);
    //public BlgUserDetail findByUsrId(BlgUser blgUser);
    public List<BlgUserDetail> findAll();
    public BlgUserDetail save(BlgUserDetail blgUsersDetail);
    public void delete(BlgUserDetail blgUsersDetail);
}
