package pl.upir.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface BlgPostService {
    public List<BlgPost> findAll();
    public Page<BlgPost> findAllByPage(Pageable pageable);
    public BlgPost findById(int id);
    public List<BlgPost> findAllByDate(String date);
    public ArrayList<BlgPost> findByTitle(String title);


    public ArrayList<BlgPost> findAllByUserId(int id);
    public List<BlgPost> findAllByUser(BlgUser blgUser);
    public List<BlgPost> findAllByUserLogin(String login);
    public List<BlgPost> findAllByUserFirstname(String firstname);
    public List<BlgPost> findAllByUserLastname(String lastname);

    public void delete(BlgPost blgPost);
    public BlgPost save(BlgPost blgPost);

}