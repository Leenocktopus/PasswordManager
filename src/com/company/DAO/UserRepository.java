package com.company.DAO;

import com.company.Connection.DBConnection;
import com.company.DAO.UserDao;
import com.company.Domain.Password;
import com.company.Domain.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository extends DBConnection implements UserDao {

    public final static String add = "INSERT INTO USER(login, password) VALUES(?,?)";
    public final static String selectByLogin = "SELECT password FROM USER WHERE login=?";
    public final static String update = "UPDATE USER SET password=? WHERE login=?";
    public final static String delete = "DELETE FROM USER WHERE login=?";

    @Override
    public boolean add(User user){
        boolean added = false;
        try( Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(add)){
             preparedStatement.setString(1, user.getLogin());
             preparedStatement.setString(2, DigestUtils.sha256Hex(user.getPassword()));
             preparedStatement.executeUpdate();
             added =true;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on USER > INSERT ");
        }
        return added;

    }

    @Override
    public boolean checkPasswordByLogin(String login, String password) {
        boolean right =  false;
        password = DigestUtils.sha256Hex(password);
        try( Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectByLogin)){
            preparedStatement.setString(1, login);
            right = preparedStatement.executeQuery().getString("password").equals(password);
        }catch (SQLException e){
           e.printStackTrace();
           System.out.println("Exception on USER > GET PASSWORD BY LOGIN ");
        }
        return right;

    }

    @Override
    public void update(User user) {
        try( Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)){
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on USER > UPDATE USER ");
        }
    }

    @Override
    public void remove(User user) {
        try( Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception on USER > DELETE USER ");
        }
    }
}
