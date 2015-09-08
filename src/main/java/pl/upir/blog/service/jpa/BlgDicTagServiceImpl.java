package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgDicTag;
import pl.upir.blog.repository.BlgDicTagRepository;
import pl.upir.blog.service.BlgDicTagService;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Service("blgDicTagService")
@Transactional
@Repository
public class BlgDicTagServiceImpl implements BlgDicTagService {

    @Autowired
    BlgDicTagRepository blgDicTagRepository;

    @Override
    public BlgDicTag findById(int id) {
        return blgDicTagRepository.findOne(id);
    }

    @Override
    public BlgDicTag finByDicTagName(String dicTagName) {
        return blgDicTagRepository.findByDicTagName(dicTagName);
    }

    @Override
    public List<BlgDicTag> findAll() {
        return Lists.newArrayList(blgDicTagRepository.findAll());
    }

    @Override
    public BlgDicTag save(BlgDicTag blgDicTag) {
        return blgDicTagRepository.save(blgDicTag);
    }

    @Override
    public void delete(BlgDicTag blgDicTag) {
        blgDicTagRepository.delete(blgDicTag);
    }
}
