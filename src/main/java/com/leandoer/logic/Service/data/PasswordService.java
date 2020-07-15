package com.leandoer.logic.service.data;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;

import java.util.List;

public interface PasswordService {

    void save(Password password);

    List<Password> getAllPasswords(User user);

    void delete(Password password);

}
