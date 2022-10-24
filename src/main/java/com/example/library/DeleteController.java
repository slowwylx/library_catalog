package com.example.library;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class DeleteController extends MainController {
        @FXML
        private Button cancelDelitionButton;

        @FXML
        private Button confirmDeletionButton;

        @FXML
        void initialize() {
            cancelDelitionButton.setOnAction(actionEvent -> {
                cancelDelitionButton.getScene().getWindow().hide();
            });

            confirmDeletionButton.setOnAction(actionEvent -> {
                confirmDel = true;
               confirmDeletionButton.getScene().getWindow().hide();
            });
        }
}
