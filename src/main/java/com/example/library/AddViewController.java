package com.example.library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane toastAdd;

    @FXML
    private TextField toastAddAuthor;

    @FXML
    private ComboBox<?> toastAddChoosePicker;

    @FXML
    private TextField toastAddName;

    @FXML
    private Button toastConfirmAddButton;
    @FXML
    private TextField toastAddNumOfPages;

    @FXML
    private TextField toastAddYearOfIssue;
    @FXML
    void initialize() {

    }

}
