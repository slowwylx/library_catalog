package com.example.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.library.DBConnection.DBconnection;
import com.example.library.literature.Book;
import com.example.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            toastConfirmAddButton.getScene().getWindow().hide();
        });
    }

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private String rent = "2";
    private String type;
    boolean check = false;

    private void save() {
            connection = DBconnection.getDbConnection();
            String name = toastAddName.getText().trim();
            String author = toastAddAuthor.getText().trim();
            String numOfPages = toastAddNumOfPages.getText().trim();
            String yearOfIssue = toastAddYearOfIssue.getText().trim();
            type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
            if (type.equals("Book")) {
                type = "1";
            }
            System.out.println(type);
            getQuery();
            insert();
        }
        private void getQuery () {
            if (!MainController.add) {
                query = "INSERT INTO `bookcharacter`(`nameOfBook`, `author`, `pages`, `yearOfPublish`, `bookType` , `rented`) VALUES (?,?,?,?,?,?)";
                check = false;
            } else {
                check = true;
                query = "UPDATE `bookcharacter` SET " +
                        "   `nameOfBook` = ?," +
                        "   `author` = ?," +
                        "   `pages` = ?," +
                        "   `yearOfPublish` = ?," +
                        "   `bookType` = ?," +
                        "   `rented` = ? WHERE (`id` = '" + MainController.selectRow.getId() + "')";
            }
        }
        private void insert () {
            try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, toastAddName.getText());
            preparedStatement.setString(2, toastAddAuthor.getText());
            preparedStatement.setString(3, toastAddNumOfPages.getText());
            preparedStatement.setString(4, toastAddYearOfIssue.getText());
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, rent);
            if(!check) {
                preparedStatement.executeUpdate();
            }else {
                preparedStatement.execute();
            }
        }catch(SQLException ex){
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void setTextField(int id, String name, String author, int numOfPages, int yearOfIssue) {
        int bookId = id;
        toastAddName.setText(name);
        toastAddAuthor.setText(author);
        toastAddNumOfPages.setText(String.valueOf(numOfPages));
        toastAddYearOfIssue.setText(String.valueOf(yearOfIssue));
        type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
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
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
