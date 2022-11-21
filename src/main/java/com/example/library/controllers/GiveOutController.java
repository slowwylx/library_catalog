package com.example.library.controllers;
import com.example.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class GiveOutController {
    @FXML
    private Button confirmButton;
    @FXML
    private Button declineButton;
    @FXML
    void initialize() {
            confirmButton.setOnAction(actionEvent -> {
                Dlg.showWindow("sign-in-view.fxml", false,null);
                confirmButton.getScene().getWindow().hide();
            });
            declineButton.setOnAction(actionEvent -> {
                Dlg.showWindow("add-new-user.fxml", false,null);
                declineButton.getScene().getWindow().hide();
            });
    }

}
