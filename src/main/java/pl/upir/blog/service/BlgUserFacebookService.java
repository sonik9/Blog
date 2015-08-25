package pl.upir.blog.service;

import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.entity.BlgUserFacebook;

import java.util.List;

/**
 * Created by Vitalii on 22.06.2015.
 */
public interface BlgUserFacebookService {
    //@PreAuthorize("hasRole('ROLE_CLIENT')")

    public List<BlgUserFacebook> findAll();
    public BlgUserFacebook findById(Long id);
    public BlgUserFacebook save(BlgUserFacebook blgUsersFacebook);
    public void delete(BlgUserFacebook blgUsersFacebook);

}
