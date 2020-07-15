package com.leandoer.logic.service.data.impl;

import com.leandoer.logic.domain.User;
import com.leandoer.logic.repository.PasswordRepository;
import com.leandoer.logic.repository.UserRepository;
import com.leandoer.logic.service.data.UserService;
import com.leandoer.logic.service.security.Authentication;
import com.leandoer.logic.service.security.crypto.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    PasswordRepository passwordRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void createAccount(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()).orElseThrow(RuntimeException::new));
        userRepository.save(user);
    }

    @Override
    public Authentication authenticate(User user) {
        Authentication authentication = new Authentication();
        User selected = userRepository.findUserByUsername(user.getUsername());
        authentication.setUser(selected);
        authentication.setAuthenticated(passwordEncoder.encode(user.getPassword())
                .orElseThrow(RuntimeException::new).equals(selected.getPassword()));
        return authentication;
    }

    @Override
    public void deleteAccount(User user) {
        passwordRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
