package com.leandoer.ui;

import com.leandoer.config.BeansConfig;
import com.leandoer.ui.context.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

public class PasswordManager extends Application {
    AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        configureSpringContext();
        configurePrimaryStage(primaryStage, context.getBean(SpringFXMLLoader.class));
        primaryStage.show();
    }

    private void configureSpringContext() {
        context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("production");
        context.register(BeansConfig.class);
        context.refresh();
    }

    private void configurePrimaryStage(Stage primaryStage, SpringFXMLLoader loader) {
        Parent root = loader.load("views/login_window.fxml");
        primaryStage.setTitle("PM");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 245, 373));
        primaryStage.getScene().getStylesheets().add(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("style.css")).toExternalForm()
        );
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                ClassLoader.getSystemClassLoader().getResourceAsStream("views/Images/keylock.png")
        )));
    }

    @Override
    public void stop() {
        context.close();
    }

}
