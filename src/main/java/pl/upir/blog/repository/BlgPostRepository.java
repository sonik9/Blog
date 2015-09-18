package pl.upir.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgPostRepository extends PagingAndSortingRepository<BlgPost, Integer> {
    @Query(value = "SELECT * FROM blg_post WHERE DATE(pst_time_create)= :date", nativeQuery = true)
    public ArrayList<BlgPost> findAllByPstTimeCreate(@Param("date")String date);
    //@Query(value = "SELECT * FROM blg_post WHERE pst_title LIKE %:title%", nativeQuery = true)
    public ArrayList<BlgPost> findByPstTitleContaining(@Param("title")String title);
    @Query(value = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'blg_post' AND table_schema = DATABASE()", nativeQuery = true)
    public int getNextAutoincrement();

    //@Query(value = "SELECT * FROM blg_post WHERE pst_enable=true", nativeQuery = true)
    public Page<BlgPost> findByPstEnable(boolean pstEnable,Pageable pageable);

    //public Page<BlgPost> findAllAndPstEnableIsTrueByPage(Pageable pageable);

    /*public ArrayList<BlgPost> findAllByBlgUser_UsrId(int usrId);
    public ArrayList<BlgPost> findAllByBlgUser(BlgUser blgUser);

    @Query(value = "SELECT * FROM blg_post p JOIN blg_user u ON(p.usr_id=u.usr_id) WHERE u.usr_login=?1", nativeQuery = true)
    public ArrayList<BlgPost> findAllByBlgUser_UsrLogin(String usrLogin);

    public ArrayList<BlgPost> findAllByBlgUser_BlgUserDetail_UsrDetFirstnameContains(String firstname);
    public ArrayList<BlgPost> findAllByBlgUser_BlgUserDetail_UsrDetLastnameContains(String lastname);*/
}
