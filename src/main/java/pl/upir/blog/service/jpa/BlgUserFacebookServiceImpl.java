package pl.upir.blog.service.jpa;

import pl.upir.blog.entity.BlgUserFacebook;
import pl.upir.blog.repository.BlgUserFacebookRepository;
import pl.upir.blog.service.BlgUserFacebookService;

import java.util.List;

/**
 * Created by Vitalii on 12.08.2015.
 */
public class BlgUserFacebookServiceImpl implements BlgUserFacebookService {

    BlgUserFacebookRepository blgUserFacebookRepository;

    @Override
    public List<BlgUserFacebook> findAll() {
        return null;
    }

    @Override
    public BlgUserFacebook findById(Long id) {
        return blgUserFacebookRepository.findOne(id);
    }

    @Override
    public BlgUserFacebook save(BlgUserFacebook blgUsersFacebook) {
        return blgUserFacebookRepository.save(blgUsersFacebook);
    }

    @Override
    public void delete(BlgUserFacebook blgUsersFacebook) {
        blgUserFacebookRepository.delete(blgUsersFacebook);
    }
}
