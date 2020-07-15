package com.leandoer.logic.service.data;

import com.leandoer.logic.domain.User;
import com.leandoer.logic.service.security.Authentication;

public interface UserService {

    void createAccount(User user);

    Authentication authenticate(User user);

    void deleteAccount(User user);
}
