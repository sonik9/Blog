package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgUser;
import pl.upir.blog.repository.BlgUserRepository;
import pl.upir.blog.service.BlgUserService;

import java.util.List;

/**
 * Created by Vitalii on 22.06.2015.
 */
@Service("blgUserService")
@Repository
@Transactional
public class BlgUserServiceImpl implements BlgUserService {

    @Autowired
    BlgUserRepository blgUserRepository;


    @Override
    @Transactional(readOnly = true)
    public List<BlgUser> findAll() {
        return Lists.newArrayList(blgUserRepository.findAll());
    }

    @Override
    public BlgUser findById(int id) {
        return blgUserRepository.findOne(id);
    }

    @Override
    public BlgUser save(BlgUser blgUsers) {
        return blgUserRepository.save(blgUsers);
    }

    @Override
    public List<BlgUser> findByusrLogin(String usrLogin) {
        return null;
    }

    @Override
    public void delete(BlgUser blgUsers) {
        blgUserRepository.delete(blgUsers);
    }
}
