package com.leandoer.ui.controller;

import com.leandoer.logic.domain.User;
import com.leandoer.logic.service.data.UserService;
import com.leandoer.ui.context.SpringFXMLLoader;
import com.leandoer.ui.controller.util.AlertService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUserController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationField;

    private UserService userService;
    private SpringFXMLLoader loader;
    private AlertService alertService;

    @Autowired
    public NewUserController(UserService userService, SpringFXMLLoader loader, AlertService alertService) {
        this.userService = userService;
        this.loader = loader;
        this.alertService = alertService;
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        if (!passwordField.getText().isEmpty() && !loginField.getText().isEmpty()) {
            if (passwordField.getText().equals(confirmationField.getText())) {
                User user = new User();
                user.setUsername(loginField.getText());
                user.setPassword(passwordField.getText());
                try {
                    userService.createAccount(user);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //1. Do something if index violation
                    //2. Do something if password and/or username value is too long or too short
                }
                loader.switchScene(actionEvent, "views/login_window.fxml");
            } else {
                alertService.getAlert(Alert.AlertType.ERROR, "Error", "Passwords don't match!", null).showAndWait();
            }
        } else {
            String style = "-fx-border-color: #B00020";
            passwordField.setStyle(style);
            loginField.setStyle(style);
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        loader.switchScene(actionEvent, "views/login_window.fxml");
    }
}
