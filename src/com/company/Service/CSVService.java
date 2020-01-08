package com.company.Service;

import com.company.DAO.PasswordDao;
import com.company.Domain.Password;
import com.company.Domain.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {

    public static boolean importCSV(String path, User user){
        boolean imported = false;
        try{
        List<Password> s = Files.readAllLines(Paths.get(path))
                .stream()
                .map(x->x.split(","))
                .map(x-> new Password(x[0],x[1],x[2],x[3],x[4]))
                .collect(Collectors.toList());
        PasswordDao passwordDao = new PasswordService(user);
        s.forEach(passwordDao::add);
        imported = true;
        } catch (IOException e){
            e.printStackTrace();
        }
        return imported;
    }

    public static boolean exportCSV(String path, User user){
        boolean exported = false;
        try{
            PasswordDao passwordDao = new PasswordService(user);
            List<String> s = passwordDao.getAllPasswords()
                    .stream()
                    .map(Password::toCSV)
                    .collect(Collectors.toList());
            Files.write(Paths.get(path),s);
            exported = true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return exported;
    }


}
