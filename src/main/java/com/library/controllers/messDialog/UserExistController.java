package com.library.controllers.messDialog;

import com.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserExistController{

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
