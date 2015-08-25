package pl.upir.blog.repository;

import org.springframework.data.repository.CrudRepository;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserFacebook;

/**
 * Created by Vitalii on 23.06.2015.
 */
public interface BlgUserFacebookRepository extends CrudRepository<BlgUserFacebook, Long> {

}
