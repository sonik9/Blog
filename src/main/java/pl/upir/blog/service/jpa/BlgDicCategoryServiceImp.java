package pl.upir.blog.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.upir.blog.entity.BlgDicCategory;
import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.repository.BlgDicCategoryRepository;
import pl.upir.blog.service.BlgDicCategoryService;

import javax.inject.Named;
import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
@Service("blgDicCategoryService")
@Repository
@Transactional
@Named
public class BlgDicCategoryServiceImp implements BlgDicCategoryService {

    @Autowired
    BlgDicCategoryRepository blgPostCategoriesRepository;

    @Override
    public List<BlgDicCategory> findAll() {
        return Lists.newArrayList(blgPostCategoriesRepository.findAll());
    }

    @Override
    public List<BlgDicCategory> findAllByEnable(Boolean enable) {
        return blgPostCategoriesRepository.findAllByDicCatEnable(enable);
    }

    @Override
    public BlgDicCategory findById(int pstCatId) {
        return blgPostCategoriesRepository.findOne(pstCatId);
    }

    @Override
    public BlgDicCategory findByTitle(String pstCatTitle) {
        return blgPostCategoriesRepository.findByDicCatNameContains(pstCatTitle);
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
    public void delete(BlgDicCategory blgPostCategories) {
        blgPostCategoriesRepository.delete(blgPostCategories);
    }

    @Override
    public BlgDicCategory save(BlgDicCategory blgPostCategories) {
        return blgPostCategoriesRepository.save(blgPostCategories);
    }

    @Override
    public Page<BlgPost> findByCatPstEnable(boolean pstEnable, String dicCatName, Pageable pageable) {
        return null;//blgPostCategoriesRepository.fin(pstEnable,dicCatName,pageable);
    }
}
