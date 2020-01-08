package UI.Controllers;

import UI.Main;
import com.company.DAO.UserDao;
import com.company.Domain.Password;
import com.company.Domain.User;
import com.company.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class NewUserController {
    private final UserDao userDao = new UserService();
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField1;
    @FXML private PasswordField passwordField2;
    @FXML private Label errorLabel;

    public void cancel(ActionEvent actionEvent) {
        Main m = new Main();
        try{
            m.switchScene("Views/login_window.fxml");}
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent actionEvent) {
            errorLabel.setVisible(false);
        if (loginField.getText().isEmpty()){
            errorLabel.setText("Login can't be empty.");
            errorLabel.setVisible(true);
            return;
        }
        if (passwordField1.getText().equals(passwordField2.getText())){

            User user = new User(loginField.getText(), passwordField1.getText());
            if(userDao.add(user)){
            Main m = new Main();
            try{
                m.switchScene("Views/login_window.fxml");}
            catch (IOException e){
                e.printStackTrace();
            }}else{
                errorLabel.setText("User Already Exists.");
                errorLabel.setVisible(true);
            }
        }else {

            errorLabel.setText("Passwords are not the same.");
            errorLabel.setVisible(true);
        }
    }
    public void signUpKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            signUp(new ActionEvent());
        }
    }
}
