package com.example.library.controllers;

import com.example.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserExistController extends AddNewUserController {

    @FXML
    private Button logInButton;

    @FXML
    void initialize() {
        logInButton.setOnAction(actionEvent -> {
            Dlg.showWindow("sign-in-view.fxml", false,null);
            logInButton.getScene().getWindow().hide();
        });
    }
}
