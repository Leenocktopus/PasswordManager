package com.leandoer.logic.service.data.impl;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;
import com.leandoer.logic.repository.PasswordRepository;
import com.leandoer.logic.service.data.PasswordService;
import com.leandoer.logic.service.security.crypto.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PasswordServiceImpl implements PasswordService {
    PasswordRepository passwordRepository;
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void save(Password password) {
        password.setPassword(encryptionService.encrypt(password.getPassword(),"mock")
                .orElseThrow(RuntimeException::new));
        passwordRepository.save(password);
    }

    @Override
    public List<Password> getAllPasswords(User user) {
        List<Password> passwords = passwordRepository.findAllByUser(user);
        passwords
                .stream()
                .parallel()
                .forEach(password -> password.setPassword(encryptionService.decrypt(password.getPassword(), "mock")
                        .orElseThrow(RuntimeException::new)));
        return passwords;
    }

    @Override
    public void delete(Password password) {
        passwordRepository.delete(password);
    }
}
