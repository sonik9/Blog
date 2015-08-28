package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgPostCategories;
import pl.upir.blog.repository.BlgPostCategoriesRepository;
import pl.upir.blog.service.BlgPostCategoriesService;

import javax.inject.Named;
import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Service("blgPostCategoriesService")
@Repository
@Transactional
@Named
public class BlgPostCategoriesServiceImpl implements BlgPostCategoriesService {

    @Autowired
    BlgPostCategoriesRepository blgPostCategoriesRepository;

    @Override
    public List<BlgPostCategories> findAll() {
        return Lists.newArrayList(blgPostCategoriesRepository.findAll());
    }

    @Override
    public List<BlgPostCategories> findAllByEnable(Boolean enable) {
        return blgPostCategoriesRepository.findAllByPstCatEnable(enable);
    }

    @Override
    public BlgPostCategories findById(int pstCatId) {
        return blgPostCategoriesRepository.findOne(pstCatId);
    }

    @Override
    public BlgPostCategories findByTitle(String pstCatTitle) {
        return blgPostCategoriesRepository.findByPstCatNameContains(pstCatTitle);
    }

    @Override
    public int getCountPostInById(int pstCatId) {
        return blgPostCategoriesRepository.getCountPostInById(pstCatId);
    }

    @Override
    public int getCountPostInByTitle(String pstCatTitle) {
        return blgPostCategoriesRepository.getCountPostInByTitle(pstCatTitle);
    }

    @Override
    public void delete(BlgPostCategories blgPostCategories) {
        blgPostCategoriesRepository.delete(blgPostCategories);
    }

    @Override
    public BlgPostCategories save(BlgPostCategories blgPostCategories) {
        return blgPostCategoriesRepository.save(blgPostCategories);
    }
}
