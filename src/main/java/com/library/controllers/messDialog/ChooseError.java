package com.library.controllers.messDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChooseError {

    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        okButton.setOnAction(actionEvent -> {
            okButton.getScene().getWindow().hide();
        });
    }
}
