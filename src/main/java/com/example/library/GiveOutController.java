package com.example.library;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GiveOutController extends MainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {

//        signInButton.setOnAction(actionEvent -> {
//            signIN_B();
//        });
    }

    public void signIN_B() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LibraryApplication.class.getResource("sign-in-view.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}


