package com.example.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AlreadyRentedController {


    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        okButton.setOnAction(actionEvent -> {
            okButton.getScene().getWindow().hide();
        });
    }

}
