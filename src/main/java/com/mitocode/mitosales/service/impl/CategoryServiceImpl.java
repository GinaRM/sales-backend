package com.mitocode.mitosales.service.impl;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import com.mitocode.mitosales.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mitocode.mitosales.repo.ICategoryRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

    private final ICategoryRepo repo;


    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Category> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Category> findByNameLikeIgnoreCase(String name) {
        return repo.findByNameLikeIgnoreCase("%" + name + "%");
    }

    @Override
    public List<Category> findByNameAndEnabled(String name, boolean enabled) {
        return repo.findByNameAndEnabled(name, enabled);
    }

    @Override
    public List<Category> getNameAndDescription(String name, String description) {
        return repo.getNameAndDescription(name, description);
    }

    @Override
    public List<Category> getNameAndDescription2(String name, String description) {
        return repo.getNameAndDescription2(name, description);
    }

    @Override
    public List<Category> getNameSQL(String name) {
        return repo.getNameSQL(name);
    }

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Category> findAllOrder(String param) {
        Sort.Direction direction = param.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction, "name"));
    }
}
