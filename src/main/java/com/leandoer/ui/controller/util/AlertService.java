package com.leandoer.ui.controller.util;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AlertService {


    public Alert getAlert(Alert.AlertType alertType, String titleText, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        decorateAlert(alert);
        return alert;
    }

    private void decorateAlert(Alert alert) {
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("alert.css"))
                        .toExternalForm());
        pane.getStyleClass().add("alert");
    }
}
