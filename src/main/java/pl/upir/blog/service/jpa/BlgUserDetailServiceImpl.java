package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgUserDetail;
import pl.upir.blog.repository.BlgUserDetailRepository;
import pl.upir.blog.service.BlgUserDetailService;

import java.util.List;

/**
 * Created by Vitalii on 01.07.2015.
 */
@Service("blgUserDetailService")
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BlgUserDetailServiceImpl implements BlgUserDetailService {

    @Autowired
    BlgUserDetailRepository blgUserDetailRepository;

    @Override
    public BlgUserDetail findById(int id) {
        return blgUserDetailRepository.findOne(id);
    }

    @Override
    public List<BlgUserDetail> findAll() {
        return Lists.newArrayList(blgUserDetailRepository.findAll());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BlgUserDetail save(BlgUserDetail blgUsersDetail) {
        return blgUserDetailRepository.save(blgUsersDetail);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(BlgUserDetail blgUsersDetail) {
        blgUserDetailRepository.delete(blgUsersDetail);
    }
}
