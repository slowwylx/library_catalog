package com.example.library;
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
                //Dlg.showWindow("Choose the user", "sign-in-view.fxml", false);
            });
            declineButton.setOnAction(actionEvent -> {
                Dlg.showWindow("add-new-user.fxml", false,null);
                //Dlg.showWindow("Add new user", "add-new-user.fxml", false);
            });
    }

}
