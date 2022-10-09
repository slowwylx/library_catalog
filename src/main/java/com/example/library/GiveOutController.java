package com.example.library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GiveOutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button confirmButton;

    @FXML
    private Button declineButton;

    @FXML
    void initialize() {
            confirmButton.setOnAction(actionEvent -> {
                Dlg.showWindow("Choose the user", "sign-in-view.fxml", false);
            });
            declineButton.setOnAction(actionEvent -> {
                Dlg.showWindow("Add new user", "add-new-user.fxml", false);
            });

    }

}
