package pl.upir.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.upir.blog.entity.BlgUserMail;

/**
 * Created by Vitalii on 01.07.2015.
 */
public interface BlgUserMailRepository extends PagingAndSortingRepository<BlgUserMail, Integer> {

}
