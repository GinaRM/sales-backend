package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.model.Category;
import com.mitocode.mitosales.model.Client;
import com.mitocode.mitosales.repo.IClientRepo;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements IClientService {

    private final IClientRepo repo;


    @Override
    protected IGenericRepo<Client, Integer> getRepo() {
        return repo;
    }
}
