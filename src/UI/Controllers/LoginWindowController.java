package UI.Controllers;

import UI.Main;
import com.company.DAO.UserDao;
import com.company.Domain.User;
import com.company.DAO.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class LoginWindowController {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    private static final UserDao userDao = new UserRepository();
    public void signUp(ActionEvent actionEvent) {
        Main m = new Main();
        try{
            m.switchScene("Views/new_user_window.fxml");}
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void signIn(ActionEvent actionEvent) {
        errorLabel.setVisible(false);
        if (userDao.checkPasswordByLogin(loginField.getText(), passwordField.getText())){
        Main m = new Main();
        try{
            MainController.user = new User(loginField.getText(), passwordField.getText());
            m.switchScene("Views/main_window.fxml");

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR WHILE SWITCHING FX SCENE");
        }
        }else{
            errorLabel.setText("Login or password are incorrect. Please, try again.");
            errorLabel.setVisible(true);
        }
    }

    public void signInKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            signIn(new ActionEvent());
        }
    }
}
