package com.mitocode.mitosales.repo;

import com.mitocode.mitosales.model.User;


public interface IUserRepo extends IGenericRepo<User, Integer> {

    User findOneByUsername(String username);

}
