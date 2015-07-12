package pl.upir.blog.service;

import pl.upir.blog.entity.BlgUserMail;

import java.util.List;

/**
 * Created by Vitalii on 01.07.2015.
 */
public interface BlgUserMailService {

    public List<BlgUserMail> findAll();
    public BlgUserMail findById(int id);
    public BlgUserMail save(BlgUserMail blgUserMail);
    public void delete(BlgUserMail blgUserMail);
}
