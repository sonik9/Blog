package pl.upir.blog.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.repository.BlgDicRoleRepository;
import pl.upir.blog.service.BlgDicRoleService;

import java.util.List;

/**
 * Created by Vitalii on 29.07.2015.
 */
@Service("blgDicRoleService")
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BlgDicRoleServiceImpl implements BlgDicRoleService {

    @Autowired
    BlgDicRoleRepository blgDicRoleRepository;

    @Override
    public BlgDicRole findById(int id) {
        return blgDicRoleRepository.findOne(id);
    }



    @Override
    public List<BlgDicRole> findAll() {
        return null;
    }

    @Override
    public BlgDicRole save(BlgDicRole blgDicRole) {
        return null;
    }

    @Override
    public void delete(BlgDicRole blgDicRole) {

    }
}
