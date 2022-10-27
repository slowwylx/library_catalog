package com.example.library;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class DeleteController {
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
                MainController.confirmDel = true;
               confirmDeletionButton.getScene().getWindow().hide();
            });
        }
}
