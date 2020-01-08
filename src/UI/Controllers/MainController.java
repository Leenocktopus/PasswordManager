package UI.Controllers;

import UI.Main;
import com.company.DAO.PasswordDao;
import com.company.DAO.UserDao;
import com.company.Domain.Password;
import com.company.Domain.User;
import com.company.Service.CSVService;
import com.company.Service.PasswordService;
import com.company.Service.UserService;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainController {
    public static User user;
    private static UserDao userDao = new UserService();
    private static PasswordDao passwordDao;
    public TextField searchField;
    private List<Password> pl;
    public  ListView<Password> passwordList =  new ListView<>();

    @FXML
    private void initialize(){
        passwordDao = new PasswordService(user);
        pl = passwordDao.getAllPasswords();
        passwordList.setItems(FXCollections.observableList(pl));
    }



    public void importFromCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"),
                                                 new FileChooser.ExtensionFilter("All files", "*.*"));
        File f = fileChooser.showOpenDialog(null);
        if(f!=null){
        CSVService.importCSV(f.getAbsolutePath(), user);
            pl = passwordDao.getAllPasswords();
            passwordList.setItems(FXCollections.observableList(pl));
            passwordList.refresh();
        }
    }


    public void exportToCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fcef = new FileChooser.ExtensionFilter("All files", "*.*");
        fileChooser.getExtensionFilters().add(fcef);
        fileChooser.setInitialFileName("file.csv");
        File f = fileChooser.showSaveDialog(null);
        if(f!=null){
        CSVService.exportCSV(f.getAbsolutePath(), user);}
    }

    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText(null);
        alert.setHeaderText("Password manager v 1.0. \nDeveloped by Alexey Raichev.");
        alert.showAndWait();
    }

    public void deleteAccount(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setContentText("This action cannot be undone. " +
                "All your passwords will be deleted with your account." +
                "You might consider saving your passwords to csv: 'File->Export to CSV'");
        dialog.setContentText("Please, type '"+ user.getLogin()+"' to confirm.");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent() && result.get().equals(user.getLogin())){
        userDao.remove(user);
        passwordDao.deleteAllForUser();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User deleted");
        alert.setContentText(null);
        alert.setHeaderText("Successfully deleted account.");
        alert.show();
        Main m = new Main();
        try{
            m.switchScene("Views/login_window.fxml");}
        catch (IOException e){
            e.printStackTrace();
        }
    }else if (result.isPresent()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(null);
            alert.setHeaderText("Error while deleting account. Please try again.");
            alert.show();
        }
    }

    public void add(ActionEvent actionEvent) {
        Main m = new Main();
        try{
            m.switchScene("Views/password_window.fxml");}
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void remove(ActionEvent actionEvent) {
        for(Integer i : passwordList.getSelectionModel().getSelectedIndices()){
            passwordDao.remove(pl.get(i));
            pl.remove(pl.get(i));
        }
        passwordList.refresh();
    }

    public void edit(MouseEvent actionEvent){
        if(actionEvent.getButton().equals(MouseButton.PRIMARY) && actionEvent.getClickCount()==2){
        int index = passwordList.getSelectionModel().getSelectedIndices().get(0);
        if (index==-1) return;
        Main m = new Main();
        try{
            PasswordWindowController.l =pl.get(index).getLogin();
            PasswordWindowController.p =pl.get(index).getPassword();
            PasswordWindowController.u =pl.get(index).getResourceUrl();
            PasswordWindowController.d =pl.get(index).getDesc();
            PasswordWindowController.n =pl.get(index).getNotes();

            m.switchScene("Views/password_window.fxml");

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR WHILE SWITCHING FX SCENE");
        }

    }
    }

    public void logout(ActionEvent actionEvent) {
        Main m = new Main();
        try{
            m.switchScene("Views/login_window.fxml");}
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void search(ActionEvent actionEvent) {
        pl = passwordDao.getAllPasswords();
        pl = pl.stream().filter(x -> x.toCSV().contains(searchField.getText().trim()))
                .collect(Collectors.toList());
        passwordList.setItems(FXCollections.observableList(pl));
        passwordList.refresh();
    }

    public void stopSearch(ActionEvent actionEvent) {
        pl = passwordDao.getAllPasswords();
        passwordList.setItems(FXCollections.observableList(pl));
        passwordList.refresh();
    }
}
