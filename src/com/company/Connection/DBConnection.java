package com.company.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBConnection {
    public static final String createTable1 = "CREATE TABLE if not EXISTS user (\n" +
                                                "login VARCHAR(20) NOT NULL,\n" +
                                                "password VARCHAR(20),\n" +
                                                "PRIMARY KEY (login));\n";
    public static final String createTable2 = "CREATE TABLE if not EXISTS passwords(\n" +
                                                "login VARCHAR(20) NOT NULL,\n" +
                                                "password VARCHAR(20),\n" +
                                                "url VARCHAR(40),\n" +
                                                "user_id VARCHAR(20),"+
                                                "desc VARCHAR(30),"+
                                                "notes VARCHAR(100),"+
                                                "PRIMARY KEY (login, password, url, user_id));";

    public Connection getConnection(){
        Connection c = null;
        try{
            c = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement s = c.createStatement();
            s.executeUpdate(createTable1);
            s.executeUpdate(createTable2);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

}
