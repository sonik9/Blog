package pl.upir.blog.service;

import pl.upir.blog.entity.BlgDicRole;
import pl.upir.blog.entity.BlgUserDetail;

import java.util.List;

/**
 * Created by Vitalii on 29.07.2015.
 */
public interface BlgDicRoleService {
    public BlgDicRole findById(int id);
    public List<BlgDicRole> findAll();
    public BlgDicRole save(BlgDicRole blgDicRole);
    public void delete(BlgDicRole blgDicRole);
}
