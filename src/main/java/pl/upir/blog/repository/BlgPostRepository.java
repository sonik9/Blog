package pl.upir.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgUser;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgPostRepository extends JpaRepository<BlgPost, Integer> {//PagingAndSortingRepository<BlgPost, Integer> {
    @Query(value = "SELECT * FROM blg_post WHERE DATE(pst_time_create)= :date", nativeQuery = true)
    public ArrayList<BlgPost> findAllByPstTimeCreate(@Param("date")String date);

    public ArrayList<BlgPost> findByPstTitleContaining(@Param("title")String title);

    @Query(value = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'blg_post' AND table_schema = DATABASE()", nativeQuery = true)
    public int getNextAutoincrement();

    public Page<BlgPost> findByPstEnable(boolean pstEnable,Pageable pageable);

    @Query(value = "SELECT distinct p FROM BlgPost p\n"+
            "JOIN p.blgDicCategorySet bpc\n" +
            //"JOIN BlgDicCategory bdc ON(bdc.dicCatId = bpc.dicCatId)\n" +
            "WHERE bpc.dicCatName=:category and p.pstEnable=:pstEnable"
    /*countQuery = "SELECT count(p) FROM BlgPost p\n"+
            "JOIN p.blgDicCategorySet bpc \n" +
            //"JOIN BlgDicCategory bdc ON(bdc.dicCatId = bpc.dicCatId)\n" +
            "WHERE bpc.dicCatName=:category and p.pstEnable=:pstEnable",*/)
    public Page<BlgPost> findByCatAndPstEnable(@Param("category") String category,@Param("pstEnable") boolean pstEnable,Pageable pageable);

    @Query(value = "SELECT distinct p FROM BlgPost p\n"+
            "JOIN p.blgDicTagSet bdt\n" +
            "WHERE bdt.dicTagName=:tag and p.pstEnable=:pstEnable"
    )
    public Page<BlgPost> findByTagAndPstEnable(@Param("tag") String tag,@Param("pstEnable") boolean pstEnable,Pageable pageable);

    @Query(value = "SELECT distinct p.pstTimeCreate FROM BlgPost p WHERE p.pstEnable=:pstEnable")
    public ArrayList<Date> findAllDateAndPstEnable(@Param("pstEnable") boolean pstEnable);

    @Query(value = "SELECT distinct p FROM BlgPost p WHERE year(p.pstTimeCreate)=:year AND " +
            "month(p.pstTimeCreate)=:month AND p.pstEnable=:pstEnable")
    public Page<BlgPost> findByPstEnableAndLikeDate(@Param("pstEnable") boolean pstEnable,
                                                      @Param("year") Integer year, @Param("month") Integer month, Pageable pageable);


    /*public ArrayList<BlgPost> findAllByBlgUser_UsrId(int usrId);
    public ArrayList<BlgPost> findAllByBlgUser(BlgUser blgUser);

    @Query(value = "SELECT * FROM blg_post p JOIN blg_user u ON(p.usr_id=u.usr_id) WHERE u.usr_login=?1", nativeQuery = true)
    public ArrayList<BlgPost> findAllByBlgUser_UsrLogin(String usrLogin);

    public ArrayList<BlgPost> findAllByBlgUser_BlgUserDetail_UsrDetFirstnameContains(String firstname);
    public ArrayList<BlgPost> findAllByBlgUser_BlgUserDetail_UsrDetLastnameContains(String lastname);*/
}
