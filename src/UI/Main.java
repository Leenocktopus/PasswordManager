package UI;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private static Stage primaryStage;


    @Override
    public void start(Stage ps) throws IOException {
        primaryStage = ps;
        Parent root = FXMLLoader.load(getClass().getResource("Views/login_window.fxml"));
        primaryStage.setTitle("Password Manager");//
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400,400));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Views/Images/keylock.png")));
        primaryStage.show();

    }
    public void switchScene(String fxml) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.getScene().setRoot(root);
    }
}
