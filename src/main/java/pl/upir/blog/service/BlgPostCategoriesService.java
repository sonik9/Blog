package pl.upir.blog.service;

import pl.upir.blog.entity.BlgPost;
import pl.upir.blog.entity.BlgPostCategories;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgPostCategoriesService {

    public List<BlgPostCategories> findAll();
    public List<BlgPostCategories> findAllByEnable(Boolean enable);
    public BlgPostCategories findById(int pstCatId);
    public BlgPostCategories findByTitle(String pstCatTitle);

    public int getCountPostInById(int pstCatId);
    public int getCountPostInByTitle(String pstCatTitle);

    public void delete(BlgPostCategories blgPostCategories);
    public BlgPostCategories save(BlgPostCategories blgPostCategories);
}
