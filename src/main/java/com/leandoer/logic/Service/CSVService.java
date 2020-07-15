package com.leandoer.logic.service;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;
import com.leandoer.logic.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVService {

    PasswordRepository passwordRepository;

    @Autowired
    public CSVService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public void importFromCSV(String path, User user) {
        try {
            Files.readAllLines(Paths.get(path)).stream().skip(1)
                    .filter(line -> !line.matches("^\\s*$"))
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(line -> line.split(","))
                    .map(array -> {
                        Password password = new Password();
                        password.setUsername(array[0]);
                        password.setPassword(array[1]);
                        password.setResourceUrl(array[2]);
                        password.setDescription(array[3]);
                        password.setUser(user);
                        return password;
                    })
                    .forEach(passwordRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToCSV(String path, User user) {

        List<String> selectedPasswords = new ArrayList<>(Arrays.asList("username, password, resource_url, description"));
        System.out.println(Arrays.toString(passwordRepository.findAllByUser(user).toArray()));
        passwordRepository.findAllByUser(user).stream()
                .map(password -> password.getUsername() + ", " + password.getPassword() + ", "
                        + password.getResourceUrl() + ", " + password.getDescription())
                .forEach(selectedPasswords::add);
        try {
            Files.write(Paths.get(path), selectedPasswords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
