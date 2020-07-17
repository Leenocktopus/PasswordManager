package com.leandoer.ui.controller;

import com.leandoer.logic.domain.User;
import com.leandoer.logic.service.data.UserService;
import com.leandoer.logic.service.security.Authentication;
import com.leandoer.ui.context.SpringFXMLLoader;
import com.leandoer.ui.context.UserHolder;
import com.leandoer.ui.controller.util.AlertService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginWindowController {
    UserHolder userContextHolder;
    AlertService alertService;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    private UserService userService;
    private SpringFXMLLoader loader;

    @Autowired
    public LoginWindowController(UserService userService,
                                 SpringFXMLLoader loader,
                                 UserHolder userContextHolder,
                                 AlertService alertService) {
        this.userService = userService;
        this.loader = loader;
        this.userContextHolder = userContextHolder;
        this.alertService = alertService;
    }

    public void signUp(ActionEvent actionEvent) {
        loader.switchScene(actionEvent, "views/new_user_window.fxml");
    }


    public void signIn(ActionEvent actionEvent) {
        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(passwordField.getText());
        // Not the best code
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            // 1. Do something if there is no such username
        }
        if (authentication.isAuthenticated()) {
            userContextHolder.setUser(authentication.getUser());
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loader.switchScene(actionEvent, "views/main_window.fxml");
        } else {
            alertService.getAlert(Alert.AlertType.ERROR, "Error", "Password is not correct!", null).showAndWait();

        }
    }
}
