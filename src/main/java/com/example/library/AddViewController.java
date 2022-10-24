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
import com.example.library.literature.Book;
import com.example.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AddViewController {
    @FXML
    private TextField toastAddAuthor;

    @FXML
    private ComboBox<String> toastAddChoosePicker;

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
        loadComboBox();
        toastConfirmAddButton.setOnAction(actionEvent -> {
            save();
        });
    }

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Book book = null;


    private void save() {
        try {
            connection = DBconnection.getDbConnection();
            String name = toastAddName.getText().trim();
            String author = toastAddAuthor.getText().trim();
            String numOfPages = toastAddNumOfPages.getText().trim();
            String yearOfIssue = toastAddYearOfIssue.getText().trim();
            String type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
            if(type.equals("Book")){
                type = "1";
            }
            System.out.println(type);
            String rent = "2";

    if(!MainController.add) {
        query = "INSERT INTO `bookcharacter`(`nameOfBook`, `author`, `pages`, `yearOfPublish`, `bookType` , `rented`) VALUES (?,?,?,?,?,?)";
    }else{
        query = "UPDATE `library`.`bookcharacter` SET `nameOfBook` = '?', `pages` = '?', `yearOfPublish` = '?', `author` = '?', `bookType` = '?', `rented` = '?' WHERE (`id` = '"+book.getId()+"');";
        }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, numOfPages);
            preparedStatement.setString(4, yearOfIssue);
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, rent);
            int status =  preparedStatement.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int bookId;
    void setTextField(int id, String name, String author, String numOfPages, String yearOfIssue) {
        bookId = id;
        name = toastAddName.getText();
        author = toastAddAuthor.getText();
        numOfPages = toastAddNumOfPages.getText();
        yearOfIssue = toastAddYearOfIssue.getText();
    }
    ObservableList<Literature> literatureList = FXCollections.observableArrayList();

    private void loadComboBox() {
        try {
            connection = DBconnection.getDbConnection();
            query = "SELECT * FROM library.booktype;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ObservableList<String> comboBox = FXCollections.observableArrayList();
            while (resultSet.next()) {
                literatureList.add(new Literature((resultSet.getString("bookType"))));
                comboBox.add(resultSet.getString("bookType"));
                toastAddChoosePicker.setItems(comboBox);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
