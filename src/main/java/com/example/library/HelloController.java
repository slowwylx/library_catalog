package com.example.library;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button giveOutButton;

    @FXML
    private AnchorPane headPane;

    @FXML
    private Text headerText;

    @FXML
    private ListView<?> mainList;

    @FXML
    private VBox mainVbox;

    @FXML
    private AnchorPane middlePane;

    @FXML
    private ComboBox<?> typesOfPapers;

    @FXML
    void initialize() {
     addButton.setOnAction(actionEvent -> {

     });

    }

}
