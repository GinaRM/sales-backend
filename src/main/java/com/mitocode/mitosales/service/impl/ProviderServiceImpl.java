package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.model.Provider;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.repo.IProviderRepo;
import com.mitocode.mitosales.service.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProviderServiceImpl extends CRUDImpl<Provider, Integer> implements IProviderService {

    private final IProviderRepo repo;


    @Override
    protected IGenericRepo<Provider, Integer> getRepo() {
        return repo;
    }
}
