package UI.Controllers;

import UI.Main;
import com.company.DAO.PasswordDao;
import com.company.Domain.Password;
import com.company.DAO.PasswordRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class PasswordWindowController {
    public PasswordDao passwordDao = new PasswordRepository(MainController.user);

    public static String p;
    public static String u;
    public static String l;
    public static String d;
    public static String n;

    @FXML public TextField password;
    @FXML public TextField username;
    @FXML public TextField link;
    @FXML public TextArea notes;
    @FXML public TextField desc;
    @FXML public Button button;
    @FXML
    public void initialize(){
        username.setText(l);
        password.setText(p);
        link.setText(u);
        desc.setText(d);
        notes.setText(n);

    }

    public void close(){
        l=""; p=""; u=""; d=""; n="";
    }
    public void save(ActionEvent actionEvent) {
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(null);
            alert.setHeaderText("Username and password fields are empty. Please try again.");
            alert.show();
            username.setStyle("-fx-border-color: red");
            password.setStyle("-fx-border-color: red");
            return;
        }
        String p1 = link.getText().isEmpty()?" ":link.getText();
        String p2 = desc.getText().isEmpty()?" ":desc.getText();
        String p3 = notes.getText().isEmpty()?" ":notes.getText();

        boolean added = passwordDao.add(new Password(username.getText(),
                password.getText(), p1,
                p2,p3));
        if(added){
        Main m = new Main();
        try{
            close();
            m.switchScene("Views/main_window.fxml");
            }
        catch (IOException e){
            e.printStackTrace();
        }}
    }

    public void cancel(ActionEvent actionEvent) {
        Main m = new Main();
        try{
            close();
            m.switchScene("Views/main_window.fxml");
            }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void goToUrl(ActionEvent actionEvent) {
        try {
            String prefix = link.getText().contains("https://")?"":"https://";
            Desktop.getDesktop().browse( new URL(prefix+link.getText()).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(null);
            alert.setHeaderText("Url is not valid.");
            alert.show();
        }
    }
}
