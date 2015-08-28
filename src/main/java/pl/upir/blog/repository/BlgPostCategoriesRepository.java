package pl.upir.blog.repository;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.upir.blog.entity.BlgPostCategories;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgPostCategoriesRepository extends PagingAndSortingRepository<BlgPostCategories, Integer> {

    @Query(value = "SELECT * FROM blg_post_categories  WHERE pst_cat_enable=?1", nativeQuery = true)
    public List<BlgPostCategories> findAllByPstCatEnable(Boolean enable);

    public BlgPostCategories findByPstCatNameContains(String pstCatName);

    @Query(value = "SELECT pst_cat_pstcount FROM blg_post_categories  WHERE pst_cat_id=?1", nativeQuery = true)
    public int getCountPostInById(int pstCatId);
    @Query(value = "SELECT pst_cat_pstcount FROM blg_post_categories  WHERE pst_cat_name LIKE %?1%", nativeQuery = true)
    public int getCountPostInByTitle(String pstCatTitle);
}
