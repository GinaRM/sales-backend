package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.model.User;
import com.mitocode.mitosales.repo.IUserRepo;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo;


    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }
}
