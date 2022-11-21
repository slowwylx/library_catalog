package com.example.library.controllers;

import java.sql.*;

import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.library.DBConnection.DBconnection;
import com.example.library.Dlg;
import com.example.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.example.library.literature.Book;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static com.example.library.DBConnection.Const.*;
import static com.example.library.service.Global.*;

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
    private TableView<Book> mainTable;
    @FXML
    private TableColumn<Book, Integer> pagesCol;
    @FXML
    private TextField searchLiteratureField;
    @FXML
    private TableColumn<Book, String> tableAuthor;
    @FXML
    private TableColumn<Book, String> tableName;
    @FXML
    private TableColumn<Book, Integer> tableNumber;
    @FXML
    private TableColumn<Book, String> tableStatus;
    @FXML
    private ComboBox<String> typesOfPapers;
    @FXML
    private FontAwesomeIconView userButtonMain;
    @FXML
    private TableColumn<Book, Integer> yearOfPublishCol;
    @FXML
    private FontAwesomeIconView refreshIcon;
    @FXML
    private FontAwesomeIconView closeButton;
    private String litType;
    private AddViewController addViewController;
    private GiveOutController giveOutController;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Book book = null;


    @FXML
    void initialize() {

        loadDate();
        loadComboBox();
        search();
        addButton.setOnAction(actionEvent -> {
            add = true;
            addViewController=Dlg.showWindow("add-view.fxml",false,(AddViewController controller)->{
            });
            refreshTable();
        });
        giveOutButton.setOnAction(actionEvent -> {
            selectRow = mainTable.getSelectionModel().getSelectedItem();
            if (selectRow.getIsAvailable().equalsIgnoreCase("available")) {
                Dlg.showWindow("give-out-view.fxml", false, null);
            } else {
                Dlg.showWindow("already-rented.fxml", false, null);
            }
            refreshTable();
        });


        userButtonMain.setOnMouseClicked(mouseEvent -> {
            Dlg.showWindow("sign-in-view.fxml", false, null);
            refreshTable();
        });

        typesOfPapers.setOnAction(actionEvent -> {
            filtr(typesOfPapers.getSelectionModel().getSelectedItem());
        });

        editMainTableButton.setOnAction(actionEvent -> {
            add = false;
            selectRow = mainTable.getSelectionModel().getSelectedItem();
                addViewController=Dlg.showWindow("add-view.fxml",false,(AddViewController controller) ->{
                controller.setTextField(selectRow.getId(),
                                        selectRow.getName(),
                                        selectRow.getAuthor(),
                                        selectRow.getPages(),
                                        selectRow.getYearOfissue());
            });
            refreshTable();
        });

        deleteButton.setOnAction(actionEvent -> {
            selectRow = mainTable.getSelectionModel().getSelectedItem();
            if(selectRow.getIsAvailable().equalsIgnoreCase("rented")){
                Dlg.showWindow("errorDelete.fxml", false,null);
            }else {
                Dlg.showWindow("delete-confirm.fxml", false, null);
                if (confirmDel) {
                    deletion();
                }
            }
        });

        refreshIcon.setOnMouseClicked(mouseEvent -> {
            refreshTable();
        });
    }

    private void loadDate() {
        try {
            connection = DBconnection.getDbConnection();
            refreshTable();
            tableNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
            tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            yearOfPublishCol.setCellValueFactory(new PropertyValueFactory<>("yearOfissue"));
            tableStatus.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private void refreshTable() {
        try {
            connection = DBconnection.getDbConnection();
            bookList.clear();
            query = "SELECT * FROM library.bookcharacter;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookList.add(new Book(
                        resultSet.getInt(LITERATURE_ID),
                        resultSet.getString(LITERATURE_NAME),
                        resultSet.getInt(LITERATURE_YEAR),
                        resultSet.getInt(LITERATURE_PAGES),
                        resultSet.getString(LITERATURE_AUTHOR),
                       status(resultSet.getString(LITERATURE_AVAILABILITY))
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
   private String status(String sts){
        if(sts.equals("1")){
            sts = "Available";
        }else{
            sts = "Rented";
        }
        return sts;
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
            comboBox.add("All");
            while (resultSet.next()) {
                literatureList.add(new Literature((resultSet.getString("bookType"))));
                comboBox.add(resultSet.getString("bookType"));
            }
            typesOfPapers.setItems(comboBox);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void deletion() {
        try {
            connection = DBconnection.getDbConnection();
            book = mainTable.getSelectionModel().getSelectedItem();
            query = "DELETE FROM library.bookcharacter WHERE id  = " + book.getId();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    FilteredList<Book> filteredData = new FilteredList<>(bookList, b -> searchHelper(searchLiteratureField.getText(),book));
    private void search() {
        Predicate<Book> bookPredicate = book -> searchHelper(searchLiteratureField.getText(),book);
        searchLiteratureField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate(null);
                filteredData.setPredicate(bookPredicate);
        });
        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());
        mainTable.setItems(sortedData);
    }
    private boolean searchHelper(String text,Book book){
        if (text == null || text.isEmpty()) {
            return true;
        }
        String lowerCase = text.toLowerCase();
        if (book.getName().toLowerCase().contains(lowerCase)) {
            return true; // Filter matches name.
        } else if (book.getAuthor().toLowerCase().contains(lowerCase)) {
            return true; // Filter matches author.
        } else if (String.valueOf(book.getYearOfissue()).contains(lowerCase)) {
            return true;
        } else
            return book.getName().contains(text); // Does not match.
    }
    ObservableList<Book> category = FXCollections.observableArrayList();

    private void filtr(String litType){
            switch (litType) {
                case "Book" -> litType = "1";
                case "Magazine" -> litType = "2";
                case "Newspaper" -> litType = "3";
                case "Autoreferat" -> litType = "4";
                case "All" -> litType = "0";
            }
            try{
            connection = DBconnection.getDbConnection();
            category.clear();
            if(!litType.equals("0")) {
                query = "SELECT * FROM bookcharacter WHERE (bookType='" + litType + "')";
            }else{
                refreshTable();
            }
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    category.add(new Book(
                            resultSet.getInt(LITERATURE_ID),
                            resultSet.getString(LITERATURE_NAME),
                            resultSet.getInt(LITERATURE_YEAR),
                            resultSet.getInt(LITERATURE_PAGES),
                            resultSet.getString(LITERATURE_AUTHOR),
                            status(resultSet.getString(LITERATURE_AVAILABILITY))
                    ));
                    mainTable.setItems(category);
                }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    }
}