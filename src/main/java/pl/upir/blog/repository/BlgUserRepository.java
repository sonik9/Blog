package pl.upir.blog.repository;

import org.springframework.data.repository.CrudRepository;
import pl.upir.blog.entity.BlgUser;

import java.util.List;

/**
 * Created by Vitalii on 23.06.2015.
 */
public interface BlgUserRepository extends CrudRepository<BlgUser, Integer> {
    public BlgUser findByUsrLogin(String usrLogin);

}
