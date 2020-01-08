package com.company.Service;

import com.company.Connection.DBConnection;
import com.company.DAO.PasswordDao;
import com.company.Domain.Password;
import com.company.Domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordService extends DBConnection implements PasswordDao {
    User user;
    public final static String add = "INSERT INTO passwords(login, password, url, user_id, desc, notes) VALUES(?,?,?,?,?,?)";
    public final static String get = "SELECT  login, password, url, desc, notes " +
                                      "FROM passwords " +
                                      "WHERE user_id=?";
    public final static String update = "UPDATE passwords " +
                                        "SET login=?, password=?, url=? , desc=?, notes=?" +
                                        "WHERE login=? and password=? and url=?";
    public final static String delete = "DELETE FROM passwords " +
                                        "WHERE login=? and password=? and url=? and user_id=?";
    public final static String deleteByUser = "DELETE FROM passwords WHERE user_id=?";

     public PasswordService(User user){
         this.user = user;
     }
    @Override
    public boolean add(Password password) {
         boolean added;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(add)){
        preparedStatement.setString(1, password.getLogin());
        preparedStatement.setString(2, EncryptionService.encrypt(user.getPassword(), password.getPassword()));
        preparedStatement.setString(3, password.getResourceUrl());
        preparedStatement.setString(4, user.getLogin());
        preparedStatement.setString(5, password.getDesc());
        preparedStatement.setString(6, password.getNotes());
        preparedStatement.executeUpdate();
        added = true;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on PASSWORD > ADD");
            added = update(new Password(password.getLogin(), password.getPassword(), password.getResourceUrl(), null, null),
                    password);
        }

        return added;
    }

    @Override
  public List<Password> getAllPasswords() {
         List<Password> passwords = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(get)){
            preparedStatement.setString(1, user.getLogin());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String p1 = rs.getString("login");
                String p2 = EncryptionService.decrypt(user.getPassword(), rs.getString("password"));
                String p3 = rs.getString("url");
                String p4 = rs.getString("desc");
                String p5 = rs.getString("notes");

                passwords.add(new Password(p1,p2,p3,p4,p5));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on PASSWORD > GET");
        }
        return passwords;
    }

    @Override
    public boolean update(Password password1, Password password2) {
        boolean updated = false;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(update)){
            preparedStatement.setString(1, password2.getLogin());
            preparedStatement.setString(2, EncryptionService.encrypt(user.getPassword(), password2.getPassword()));
            preparedStatement.setString(3, password2.getResourceUrl());
            preparedStatement.setString(4, password2.getDesc());
            preparedStatement.setString(5, password2.getNotes());
            preparedStatement.setString(6, password1.getLogin());
            preparedStatement.setString(7, EncryptionService.encrypt(user.getPassword(), password1.getPassword()));
            preparedStatement.setString(8, password1.getResourceUrl());
            preparedStatement.executeUpdate();
            updated = true;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on PASSWORD > UPDATE");
        }
        return updated;
    }

    @Override
    public void remove(Password password) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(delete)){
            preparedStatement.setString(1, password.getLogin());
            preparedStatement.setString(2, EncryptionService.encrypt(user.getPassword(),password.getPassword()));
            preparedStatement.setString(3, password.getResourceUrl());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on PASSWORD > DELETE");
        }
    }

    @Override
    public void deleteAllForUser() {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByUser)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on PASSWORD > DELETE FOR USER");
        }
    }
}
