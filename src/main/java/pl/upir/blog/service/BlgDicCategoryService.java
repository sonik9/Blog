package pl.upir.blog.service;

import pl.upir.blog.entity.BlgDicCategory;

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
}
