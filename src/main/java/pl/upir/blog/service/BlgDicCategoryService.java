package pl.upir.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.upir.blog.entity.BlgDicCategory;
import pl.upir.blog.entity.BlgPost;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgDicCategoryService {

    public List<BlgDicCategory> findAll();
    public List<BlgDicCategory> findAllByEnable(Boolean enable);
    public BlgDicCategory findById(int dicCatId);
    public BlgDicCategory findByTitle(String dicCatName);

    public int getCountPostInById(int pstCatId);
    public int getCountPostInByTitle(String dicCatName);

    public void delete(BlgDicCategory blgDicCategory);
    public BlgDicCategory save(BlgDicCategory blgDicCategory);
    public Page<BlgPost> findByCatPstEnable(boolean pstEnable, String dicCatName, Pageable pageable);
}
