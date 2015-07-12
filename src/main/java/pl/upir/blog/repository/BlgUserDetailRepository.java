package pl.upir.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.upir.blog.entity.BlgUserDetail;

/**
 * Created by Vitalii on 01.07.2015.
 */
public interface BlgUserDetailRepository extends PagingAndSortingRepository<BlgUserDetail, Integer> {
}
