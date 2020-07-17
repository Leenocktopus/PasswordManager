package com.leandoer.ui.controller.util;

import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DialogService {


    public TextInputDialog getDialog(String titleText, String headerText, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titleText);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        decorateDialog(dialog);
        return dialog;
    }

    private void decorateDialog(TextInputDialog alert) {
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("dialog.css"))
                        .toExternalForm());
        pane.getStyleClass().add("dialog");
    }
}
