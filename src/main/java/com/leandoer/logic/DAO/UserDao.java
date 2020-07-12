package com.leandoer.logic.DAO;

import com.leandoer.logic.Domain.User;

public interface UserDao {
    // Create new user
    boolean add(User user);

    // Return password for current user
    boolean checkPasswordByLogin(String login, String password);

    // Update user
    void update(User user);

    // Remove User
    void remove(User user);
}
