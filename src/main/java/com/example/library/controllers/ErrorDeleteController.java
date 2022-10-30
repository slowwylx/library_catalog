package com.example.library.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ErrorDeleteController {

    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        okButton.setOnAction(actionEvent -> {
            okButton.getScene().getWindow().hide();
        });
    }

}

