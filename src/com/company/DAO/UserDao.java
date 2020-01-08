package com.company.DAO;

import com.company.Domain.Password;
import com.company.Domain.User;

import java.sql.SQLException;

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
