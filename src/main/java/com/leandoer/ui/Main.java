package com.leandoer.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private static Stage primaryStage;
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("password_manager");

    @Override
    public void start(Stage ps) throws IOException {

        primaryStage = ps;
        Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("Views/login_window.fxml"));
        primaryStage.setTitle("Password Manager");//
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400,400));
        primaryStage.getScene().getStylesheets().add(ClassLoader.getSystemClassLoader().getResource("style.css").getPath());
        primaryStage.getIcons().add(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Views/Images/keylock.png")));
        primaryStage.show();

    }
    public void switchScene(String fxml) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.getScene().setRoot(root);
    }
}
