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
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Views/login_window.fxml"));
        this.primaryStage.setTitle("Password Manager");
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(new Scene(root, 400,400));
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Views/Images/keylock.png")));
        this.primaryStage.show();

    }
    public void switchScene(String fxml) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        this.primaryStage.getScene().setRoot(root);
    }
}
