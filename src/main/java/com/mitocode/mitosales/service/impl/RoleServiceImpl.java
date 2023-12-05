package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.model.Role;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.repo.IRoleRepo;
import com.mitocode.mitosales.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo;


    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }
}
