package com.leandoer.ui.controller;

import com.leandoer.logic.domain.Password;
import com.leandoer.logic.service.CSVService;
import com.leandoer.logic.service.data.PasswordService;
import com.leandoer.logic.service.data.UserService;
import com.leandoer.ui.context.PasswordHolder;
import com.leandoer.ui.context.SpringFXMLLoader;
import com.leandoer.ui.context.UserHolder;
import com.leandoer.ui.controller.util.AlertService;
import com.leandoer.ui.controller.util.DialogService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MainWindowController {
    @FXML
    public TextField searchField;
    @FXML
    public ListView<Password> passwordList;
    public MenuBar menuBar;
    PasswordService passwordService;
    UserHolder userHolder;
    CSVService csvService;
    UserService userService;
    SpringFXMLLoader loader;
    PasswordHolder passwordHolder;
    AlertService alertService;
    DialogService dialogService;
    private List<Password> selectedPasswords;

    @Autowired
    public MainWindowController(PasswordService passwordService,
                                UserHolder userHolder,
                                CSVService csvService,
                                UserService userService,
                                SpringFXMLLoader loader,
                                PasswordHolder passwordHolder,
                                AlertService alertService,
                                DialogService dialogService) {
        this.passwordService = passwordService;
        this.userHolder = userHolder;
        this.csvService = csvService;
        this.userService = userService;
        this.loader = loader;
        this.passwordHolder = passwordHolder;
        this.alertService = alertService;
        this.dialogService = dialogService;
    }


    @FXML
    private void initialize() {
        selectedPasswords = passwordService.getAllPasswords(userHolder.getUser());
        passwordList.setItems(FXCollections.observableList(selectedPasswords));
        passwordList.setCellFactory(param -> new ListCell<Password>() {
            @Override
            protected void updateItem(Password item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getResourceUrl());
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordList.setItems(FXCollections.observableList(
                    selectedPasswords.stream()
                            .parallel()
                            .filter(x -> x.toString().contains(newValue))
                            .collect(Collectors.toList())));
            passwordList.refresh();
        });
    }


    public void importFromCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            csvService.importFromCSV(file.getPath(), userHolder.getUser());
            selectedPasswords = passwordService.getAllPasswords(userHolder.getUser());
            passwordList.setItems(FXCollections.observableList(selectedPasswords));
        }
    }


    public void exportToCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv*");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialFileName("file.csv");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File test = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        if (test != null) {
            String fileName = test.getName();
            fileName = fileName.endsWith(".csv") ? fileName : fileName.concat(".csv");
            File file = new File(fileName);
            csvService.exportToCSV(file.getPath(), userHolder.getUser());
        }
    }

    public void about(ActionEvent actionEvent) {
        alertService.getAlert(Alert.AlertType.INFORMATION, "About", "Password manager v 2.0 \nDeveloped by Alexey Raichev.", null).showAndWait();
    }

    public void deleteAccount(ActionEvent actionEvent) {
        String title = "Delete Account";
        String header = "This action cannot be undone. \n" +
                "All your passwords will be deleted with your account.\n" +
                "You might consider saving your passwords to csv:\n " +
                "'Fileleandoer->Export to CSV'";
        String content = "Please, type '" + userHolder.getUser().getUsername() + "' to confirm.";
        TextInputDialog dialog = dialogService.getDialog(title, header, content);
        Optional<String> result;
        if ((result = dialog.showAndWait()).isPresent()) {
            if (result.get().equals(userHolder.getUser().getUsername())) {
                alertService.getAlert(Alert.AlertType.INFORMATION, "User deleted", "Successfully deleted account.", null).showAndWait();
                userService.deleteAccount(userHolder.getUser());
                Stage primaryStage = (Stage) menuBar.getScene().getWindow();
                primaryStage.getScene().setRoot(loader.load("views/login_window.fxml"));
            } else {
                alertService.getAlert(Alert.AlertType.ERROR, "Error", "Please, enter correct username.", null).showAndWait();
            }
        }


    }

    public void add(ActionEvent actionEvent) {
        loader.switchScene(actionEvent, "views/password_window.fxml");
    }

    public void remove(ActionEvent actionEvent) {
        int index = passwordList.getSelectionModel().getSelectedIndices().get(0);
        if (index == -1) {
            alertService.getAlert(Alert.AlertType.INFORMATION, "Error", "No record selected.", null).showAndWait();
            return;
        }
        int realIndex = selectedPasswords.indexOf(passwordList.getItems().get(index));
        passwordService.delete(selectedPasswords.get(realIndex));
        selectedPasswords.remove(realIndex);
        passwordList.setItems(FXCollections.observableList(selectedPasswords.stream()
                .parallel()
                .filter(x -> x.toString().contains(searchField.getText()))
                .collect(Collectors.toList())));
        passwordList.refresh();

    }

    public void edit(MouseEvent actionEvent) {
        if (actionEvent.getButton().equals(MouseButton.PRIMARY) && actionEvent.getClickCount() == 2) {
            int index = passwordList.getSelectionModel().getSelectedIndices().get(0);
            if (index == -1) {
                return;
            }
            passwordHolder.setPassword(selectedPasswords.get(index));
            loader.switchScene(passwordList, "views/password_window.fxml");
        }
    }

    public void logout(ActionEvent actionEvent) {
        loader.switchScene(actionEvent, "views/login_window.fxml");
    }
}
