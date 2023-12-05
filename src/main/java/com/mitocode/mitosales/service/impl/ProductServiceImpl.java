package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.model.Product;
import com.mitocode.mitosales.repo.IProductRepo;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    private final IProductRepo repo;


    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return repo;
    }
}
