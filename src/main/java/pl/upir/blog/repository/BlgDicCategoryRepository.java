package pl.upir.blog.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.upir.blog.entity.BlgDicCategory;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgDicCategoryRepository extends PagingAndSortingRepository<BlgDicCategory, Integer> {

    @Query(value = "SELECT * FROM blg_dic_category  WHERE pst_cat_enable=?1", nativeQuery = true)
    public List<BlgDicCategory> findAllByDicCatEnable(Boolean enable);

    public BlgDicCategory findByDicCatNameContains(String dicCatName);

    @Query(value = "SELECT dic_cat_pstcount FROM blg_dic_category  WHERE dic_cat_id=?1", nativeQuery = true)
    public int getCountPostInById(int pstCatId);
    @Query(value = "SELECT dic_cat_pstcount FROM blg_dic_category  WHERE dic_cat_name LIKE %?1%", nativeQuery = true)
    public int getCountPostInByTitle(String pstCatTitle);
}
