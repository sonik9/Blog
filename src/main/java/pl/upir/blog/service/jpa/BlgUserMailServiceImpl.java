package pl.upir.blog.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgUserMail;
import pl.upir.blog.repository.BlgUserMailRepository;
import pl.upir.blog.service.BlgUserMailService;

import java.util.List;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Service("blgUserMailService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Repository
public class BlgUserMailServiceImpl implements BlgUserMailService {

    @Autowired
    BlgUserMailRepository blgUserMailRepository;

    @Override
    public List<BlgUserMail> findAll() {
        return (List<BlgUserMail>)blgUserMailRepository.findAll();
    }

    @Override
    public BlgUserMail findById(int id) {
        return blgUserMailRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public BlgUserMail save(BlgUserMail blgUserMail) {
        return blgUserMailRepository.save(blgUserMail);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(BlgUserMail blgUserMail) {
        blgUserMailRepository.delete(blgUserMail);
    }
}
