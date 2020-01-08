package com.company.DAO;

import com.company.Domain.Password;


import java.util.List;

public interface PasswordDao {
    // Create new password record
    boolean add(Password password);

    // Return password all user passwords
    List<Password> getAllPasswords();

    // Update password
    boolean update(Password password1, Password password2);

    // Remove password
    void remove(Password password);

    void deleteAllForUser();
}
