package pl.upir.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.upir.blog.entity.BlgDicTag;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgDicTagRepository extends CrudRepository<BlgDicTag, Integer> {
    public BlgDicTag findByDicTagName(String dicTagName);

}
