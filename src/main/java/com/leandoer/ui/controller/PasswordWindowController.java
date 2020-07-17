package com.leandoer.ui.controller;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.service.data.PasswordService;
import com.leandoer.ui.context.PasswordHolder;
import com.leandoer.ui.context.SpringFXMLLoader;
import com.leandoer.ui.context.UserHolder;
import com.leandoer.ui.controller.util.AlertService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class PasswordWindowController {
    @FXML
    public TextField password;
    @FXML
    public TextField username;
    @FXML
    public TextField resourceUrl;
    @FXML
    public TextField description;
    @FXML
    public Button button;
    long id;
    PasswordService passwordService;
    SpringFXMLLoader loader;
    UserHolder contextHolder;
    PasswordHolder passwordHolder;
    AlertService alertService;

    @Autowired
    public PasswordWindowController(PasswordService passwordService, SpringFXMLLoader loader,
                                    UserHolder contextHolder, PasswordHolder passwordHolder, AlertService alertService) {
        this.passwordService = passwordService;
        this.loader = loader;
        this.contextHolder = contextHolder;
        this.passwordHolder = passwordHolder;
        this.alertService = alertService;
    }

    @FXML
    public void initialize() {
        Password instance = passwordHolder.getPassword();
        if (instance != null) {
            password.setText(instance.getPassword());
            username.setText(instance.getUsername());
            resourceUrl.setText(instance.getResourceUrl());
            description.setText(instance.getDescription());
            this.id = instance.getId();
        }
    }

    public void save(ActionEvent actionEvent) {
        if (username.getText().isEmpty() || password.getText().isEmpty() || resourceUrl.getText().isEmpty()) {
            String style = "-fx-border-color: #B00020";
            username.setStyle(style);
            password.setStyle(style);
            resourceUrl.setStyle(style);
            return;
        }
        Password instance = new Password();
        instance.setUsername(username.getText());
        instance.setPassword(password.getText());
        instance.setResourceUrl(resourceUrl.getText());
        instance.setDescription(description.getText());
        instance.setUser(contextHolder.getUser());
        if (this.id != 0) {
            instance.setId(id);
        }
        try {
            passwordService.save(instance);
        } catch (Exception ex) {
            ex.printStackTrace();
            // 1. Do something about unique index violation
        }
        passwordHolder.setPassword(null);
        this.id = 0;
        loader.switchScene(actionEvent, "views/main_window.fxml");
    }

    public void cancel(ActionEvent actionEvent) {
        passwordHolder.setPassword(null);
        this.id = 0;
        loader.switchScene(actionEvent, "views/main_window.fxml");
    }

    public void goToUrl(ActionEvent actionEvent) {
        try {
            String linkText = resourceUrl.getText();
            linkText = linkText.startsWith("https://") ? linkText : "https://".concat(linkText);
            Desktop.getDesktop().browse(new URL(linkText).toURI());
        } catch (IOException | URISyntaxException e) {
            alertService.getAlert(Alert.AlertType.ERROR, "Error", "Url is not valid.", null).showAndWait();
        }
    }
}
