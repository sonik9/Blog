package pl.upir.blog.service;

import pl.upir.blog.entity.BlgDicTag;

import java.util.List;

/**
 * Created by Vitalii on 26/08/2015.
 */
public interface BlgDicTagService {
    public BlgDicTag findById(int id);
    public BlgDicTag finByDicTagName(String dicTagName);
    public List<BlgDicTag> findAll();
    public BlgDicTag save(BlgDicTag blgDicTag);
    public void delete(BlgDicTag blgDicTag);
}
