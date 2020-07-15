package com.leandoer.ui.context;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class SpringFXMLLoader {

    @Autowired
    private ApplicationContextHolder holder;


    public <T> T load(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(holder::getBean);
        loader.setLocation(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(url)));
        try {
            return loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void switchScene(ActionEvent actionEvent, String url) {
        switchScene((Node) actionEvent.getSource(), url);
    }

    public void switchScene(Node node, String url) {
        node.getScene().setRoot(load(url));
    }
}
