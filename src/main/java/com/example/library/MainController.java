package com.example.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

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
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("add-view.fxml"));
         try {
             loader.load();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         Parent root = loader.getRoot();
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setScene(new Scene(root));
         stage.showAndWait();
     });
    }
}




