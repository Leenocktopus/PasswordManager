package com.leandoer.logic.repository;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;


import java.util.List;

public interface PasswordRepository {

    void save(Password password);

    List<Password> findAllByUser(User user);

    void delete(Password password);

}
