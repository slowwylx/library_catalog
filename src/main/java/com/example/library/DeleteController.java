package com.example.library;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteController {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button cancelDelitionButton;

        @FXML
        private Button confirmDeletionButton;

        @FXML
        void initialize() {
            cancelDelitionButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) cancelDelitionButton.getScene().getWindow();
                // do what you have to do
                stage.close();
            });
        }
}
