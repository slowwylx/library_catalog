package com.example.library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UserAccountingController {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button chooseUserButton;

        @FXML
        private Button deleteUserButton;

        @FXML
        private TextField searchUser;

        @FXML
        private AnchorPane userAccANCHORpane;

        @FXML
        private Pane userAccANCHORpaneHEADER;

        @FXML
        private Text userAccANCHORpaneHEADtext;

        @FXML
        private TableColumn<?, ?> userPhoneCol;

        @FXML
        private TableColumn<?, ?> userSecondNameCol;

        @FXML
        private TableColumn<?, ?> usernameCol;

        @FXML
        private TableView<?> usersTableview;

        @FXML
        void initialize() {

        }
}

