package com.example.library;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AddNewUserController {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button confirmRegistrButton;

        @FXML
        private TextField registrNameField;

        @FXML
        private PasswordField registrPhoneField;

        @FXML
        private TextField registrSecondNameField;

        @FXML
        void initialize() {

        }
}

