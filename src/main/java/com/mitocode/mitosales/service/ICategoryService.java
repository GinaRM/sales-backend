package com.mitocode.mitosales.service;


import com.mitocode.mitosales.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService  extends ICRUD<Category, Integer> {
    List<Category> findByName(String name);
    List<Category> findByNameLikeIgnoreCase(String name);
    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> getNameAndDescription(String name,String description);
    List<Category> getNameAndDescription2(String name, String description);

    List<Category> getNameSQL(String name);
    Page<Category> findPage(Pageable pageable);

    List<Category> findAllOrder(String param);

}
