package com.library.controllers.mainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static com.library.service.Global.confirmDel;


public class DeleteController{
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
