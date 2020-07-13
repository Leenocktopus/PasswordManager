package com.leandoer.logic.repository;

import com.leandoer.logic.domain.User;

public interface UserRepository {

    void save(User user);

    User findUserByUsername(String username);

    void delete(User user);
}
