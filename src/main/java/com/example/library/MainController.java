package com.example.library;

import com.example.library.literature.Book;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class MainController {
    private Book books;

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
    private Pane headPane;

    @FXML
    private Text headerText;

    @FXML
    private TableView<?> mainTable;

    @FXML
    private VBox mainVbox;

    @FXML
    private AnchorPane middlePane;

    @FXML
    private TableColumn<?, ?> tableAuthor;

    @FXML
    private TableColumn<Book, String> tableName;

    @FXML
    private TableColumn<?, ?> tableNumber;

    @FXML
    private TableColumn<?, ?> tableStatus;

    @FXML
    private ComboBox<?> typesOfPapers;

    @FXML
    void initialize() {
     addButton.setOnAction(actionEvent -> {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(LibraryApplication.class.getResource("add-view.fxml"));
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