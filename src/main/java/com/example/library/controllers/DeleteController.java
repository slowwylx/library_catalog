package com.example.library.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import static com.example.library.service.Global.*;

public class DeleteController extends UserAccountingController {
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
