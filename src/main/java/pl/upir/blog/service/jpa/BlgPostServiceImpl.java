package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.repository.BlgPostRepository;
import pl.upir.blog.service.BlgPostService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Service("blgPostService")
@Transactional
@Repository
public class BlgPostServiceImpl implements BlgPostService {

    @Autowired
    BlgPostRepository blgPostRepository;

    @Override
    public List<BlgPost> findAll() {
        return  Lists.newArrayList(blgPostRepository.findAll());
    }

    @Override
    public BlgPost findById(int id) {
        return blgPostRepository.findOne(id);
    }

    @Override
    public List<BlgPost> findAllByDate(String date) {
        return blgPostRepository.findAllByPstTimeCreate(date);
    }


    @Override
    public ArrayList<BlgPost> findByTitle(String title) {
        return blgPostRepository.findByPstTitleContaining(title);
    }

    @Override
    public int getNextAutoincrement() {
        return blgPostRepository.getNextAutoincrement();
    }


    @Override
    public ArrayList<BlgPost> findAllByUserId(int id) {
        return null; // blgPostRepository.findAllByBlgUser_UsrId(id);
    }

    @Override
    public List<BlgPost> findAllByUser(BlgUser blgUser) {
        return null; // blgPostRepository.findAllByBlgUser(blgUser);
    }

    @Override
    public List<BlgPost> findAllByUserLogin(String login) {
        return null; //blgPostRepository.findAllByBlgUser_UsrLogin(login);
    }

    @Override
    public List<BlgPost> findAllByUserFirstname(String firstname) {
        return null; //blgPostRepository.findAllByBlgUser_BlgUserDetail_UsrDetFirstnameContains(firstname);
    }

    @Override
    public List<BlgPost> findAllByUserLastname(String lastname) {
        return null; //blgPostRepository.findAllByBlgUser_BlgUserDetail_UsrDetLastnameContains(lastname);
    }

    @Override
    public void delete(BlgPost blgPost) {
        blgPostRepository.delete(blgPost);
    }

    @Override
    public BlgPost save(BlgPost blgPost) {
        return blgPostRepository.save(blgPost);
    }

    @Override
    public Page<BlgPost> findAllByPage(Pageable pageable) {
        return blgPostRepository.findAll(pageable);
    }

    @Override
    public Page<BlgPost> findByPstEnable(boolean pstEnable,Pageable pageable) {
        return blgPostRepository.findByPstEnable(pstEnable,pageable);
    }

    @Override
    public Page<BlgPost> findByCat(String category,boolean pstEnable, Pageable pageable) {
        return blgPostRepository.findByCatAndPstEnable(category,pstEnable,pageable);
    }

    @Override
    public Page<BlgPost> findByTag(String tag, boolean pstEnable, Pageable pageable) {
        return blgPostRepository.findByTagAndPstEnable(tag,pstEnable,pageable);
    }

    @Override
    public ArrayList<Date> findAllDateAndPstEnable(boolean pstEnable) {
        return blgPostRepository.findAllDateAndPstEnable(pstEnable);
    }

    @Override
    public Page<BlgPost> findByPstEnableAndLikeDate(boolean pstEnable, String year, Integer month, Pageable pageable) {
        return blgPostRepository.findByPstEnableAndLikeDate(pstEnable, Integer.parseInt(year), month, pageable);
    }


}
