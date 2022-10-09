package com.example.library;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.example.library.literature.Book;

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
    private Button editMainTableButton;

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
    private TableColumn<?, ?> pagesCol;

    @FXML
    private TextField searchLiteratureField;

    @FXML
    private TableColumn<?, ?> tableAuthor;

    @FXML
    private TableColumn<?, ?> tableName;

    @FXML
    private TableColumn<?, ?> tableNumber;

    @FXML
    private TableColumn<?, ?> tableStatus;

    @FXML
    private ComboBox<?> typesOfPapers;

    @FXML
    private Button userButtonMain;

    @FXML
    private TableColumn<?, ?> yearOfPublishCol;
    @FXML
    void initialize() {
        addButton.setOnAction(actionEvent -> {
            Dlg.showWindow("Book add", "add-view.fxml", false );
        });

        giveOutButton.setOnAction(actionEvent -> {
            Dlg.showWindow("Question","give-out-view.fxml",false);
        });

        userButtonMain.setOnAction(actionEvent -> {
            Dlg.showWindow("Current users", "sign-in-view.fxml", true);
        });

        editMainTableButton.setOnAction(actionEvent -> {
            Dlg.showWindow("Add/Edit", "add-view.fxml", false);
        });

        deleteButton.setOnAction(actionEvent -> {
            Dlg.showWindow("Deleting", "delete-confirm.fxml", false);
        });
    }
}