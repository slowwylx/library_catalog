package com.example.library;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.library.DBConnection.DBconnection;
import com.example.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.example.library.literature.Book;

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
    private Button editMainTableButton;

    @FXML
    private Button giveOutButton;

    @FXML
    private Pane headPane;

    @FXML
    private Text headerText;

    @FXML
    private TableView<Book> mainTable;

    @FXML
    private VBox mainVbox;

    @FXML
    private AnchorPane middlePane;

    @FXML
    private TableColumn<Book, Integer> pagesCol;

    @FXML
    private TextField searchLiteratureField;

    @FXML
    private TableColumn<Book, String> tableAuthor;

    @FXML
    private TableColumn<Book, String>  tableName;

    @FXML
    private TableColumn<Book, Integer> tableNumber;

    @FXML
    private TableColumn<Book, String> tableStatus;

    @FXML
    private ComboBox<String> typesOfPapers;

    @FXML
    private Button userButtonMain;

    @FXML
    private TableColumn<Book, Integer> yearOfPublishCol;
    @FXML
    private FontAwesomeIconView refreshIcon;
    @FXML
    void initialize() {
        loadDate();
        loadComboBox();
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

        refreshIcon.setOnMouseClicked(mouseEvent -> {
            refreshTable();
        });
    }
    private LibraryApplication mainApp;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Book books = null ;

    ObservableList<Book> bookList = FXCollections.observableArrayList();

    private void loadDate() {
        connection = DBconnection.getDbConnection();
        refreshTable();
        //tableNumber.setCellValueFactory(cellData -> cellData.getValue().getId());
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
        tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearOfPublishCol.setCellValueFactory(new PropertyValueFactory<>("yearOfissue"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

    }

    private void refreshTable() {
        try {
            bookList.clear();

            query = "SELECT * FROM library.bookcharacter;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                bookList.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("nameOfBook"),
                        resultSet.getInt("yearOfPublish"),
                        resultSet.getInt("pages"),
                        resultSet.getString("author"),
                        resultSet.getBoolean("bookType")));
                mainTable.setItems(bookList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ObservableList<Literature> literatureList = FXCollections.observableArrayList();

    private void loadComboBox() {
        try {
            connection = DBconnection.getDbConnection();
            query = "SELECT * FROM library.booktype;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ObservableList<String> comboBox = FXCollections.observableArrayList();
            while (resultSet.next()) {  // loop
                literatureList.add(new Literature((resultSet.getString("bookType"))));
                comboBox.add(resultSet.getString("bookType"));
                typesOfPapers.setItems(comboBox);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}