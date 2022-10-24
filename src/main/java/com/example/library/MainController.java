package com.example.library;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.library.DBConnection.DBconnection;
import com.example.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.example.library.literature.Book;


import static com.example.library.DBConnection.Const.*;

public class MainController {
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editMainTableButton;

    @FXML
    private Button giveOutButton;

    @FXML
    protected TableView<Book> mainTable;

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
    static boolean confirmDel = false;
    static boolean add = false;
    static Book selectRow;


    @FXML
    void initialize() {
        loadDate();
        searchLiteratureField.setOnAction(actionEvent -> {
            search();
        });
        //availableCheck();
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
    AddViewController addViewController;
        editMainTableButton.setOnAction(actionEvent -> {
//            Book selectRow = mainTable.getSelectionModel().getSelectedItem();
//                        addViewController.setTextField();
            add = true;
            Dlg.showWindow("Add/Edit", "add-view.fxml", false);
        });

        deleteButton.setOnAction(actionEvent -> {
            Dlg.showWindow("Deleting", "delete-confirm.fxml", false);
            if(confirmDel){
                deletion();
            }
        });

        refreshIcon.setOnMouseClicked(mouseEvent -> {
            refreshTable();
        });
    }

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Book book = null ;

   private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    private void loadDate() {
        try {
            connection = DBconnection.getDbConnection();
            tableNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
            tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            yearOfPublishCol.setCellValueFactory(new PropertyValueFactory<>("yearOfissue"));
            tableStatus.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void refreshTable() {
        try {
            bookList.clear();
            query = "SELECT * FROM library.bookcharacter;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                bookList.add(new Book(
                        resultSet.getInt(LITERATURE_ID),
                        resultSet.getString(LITERATURE_NAME),
                        resultSet.getInt(LITERATURE_YEAR),
                        resultSet.getInt(LITERATURE_PAGES),
                        resultSet.getString(LITERATURE_AUTHOR),
                        resultSet.getString(LITERATURE_AVAILABILITY)));
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
            refreshTable();
            query = "SELECT * FROM library.booktype;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ObservableList<String> comboBox = FXCollections.observableArrayList();
            while (resultSet.next()) {
                literatureList.add(new Literature((resultSet.getString("bookType"))));
                comboBox.add(resultSet.getString("bookType"));
                typesOfPapers.setItems(comboBox);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deletion(){
        try {
            book = mainTable.getSelectionModel().getSelectedItem();
            query = "DELETE FROM library.bookcharacter WHERE id  = "+book.getId();
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void search(){
        FilteredList<Book> filteredData = new FilteredList<>(bookList,b->true);
        searchLiteratureField.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(book -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCase = newValue.toLowerCase();
                if (book.getName().toLowerCase().contains(lowerCase)) {
                    return true; // Filter matches name.
                } else if (book.getAuthor().toLowerCase().contains(lowerCase)) {
                    return true; // Filter matches author.
                }
                else if (String.valueOf(book.getYearOfissue()).contains(lowerCase))
                    return true;
                else
                    return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        mainTable.setItems(sortedData);
    }
}