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
import javafx.scene.control.Alert;
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
    private boolean update;

    private void save() {
        try {
            connection = DBconnection.getDbConnection();
            String name = toastAddName.getText();
            String author = toastAddAuthor.getText();
            String numOfPages = toastAddNumOfPages.getText();
            String yearOfIssue = toastAddYearOfIssue.getText();
            String type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
            if(type.equals("Book")){
                type = "1";
            }
            System.out.println(type);
            String rent = "2";
            String bookid ="5";

//            INSERT INTO `library`.`bookcharacter` (`id`, `nameOfBook`, `pages`, `yearOfPublish`, `author`, `bookType`, `rented`) VALUES ('1', 'Book1', '201', 2001, 'Myloki', '1', '2');
            query = "INSERT INTO `bookcharacter`(`id`, `nameOfBook`, `author`, `pages`, `yearOfPublish`, `bookType` , `rented`) VALUES (?,?,?,?,?,?,?)";

//        else{
//            query = "UPDATE `library`.`bookcharacter` SET "  + "`nameOfBook`=?," + "`author`= ?," + "`pages`=?," + "`yearOfPublish`=?,"  + "`bookType`=?, WHERE id = '"+bookId+"'";
//        }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, numOfPages);
            preparedStatement.setString(5, yearOfIssue);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, rent);
            int status =  preparedStatement.executeUpdate();

            if(status==1){
                System.out.println("added");
            }else{
                System.out.println("dont added");
            }
        }catch(SQLException ex){
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    int bookId = 5;
//    private void getQuery() {
//        if (update == false) {
////            INSERT INTO `library`.`bookcharacter` (`id`, `nameOfBook`, `pages`, `yearOfPublish`, `author`, `bookType`, `rented`) VALUES ('1', 'Book1', '201', 2001, 'Myloki', '1', '2');
//            query = "INSERT INTO `bookcharacter`( `nameOfBook`, `author`, `pages`, `yearOfPublish`, `bookType`) VALUES (?,?,?,?,?)";
//        }else{
//            query = "UPDATE `library`.`bookcharacter` SET "  + "`nameOfBook`=?," + "`author`= ?," + "`pages`=?," + "`yearOfPublish`=?,"  + "`bookType`=?, WHERE id = '"+bookId+"'";
//        }
//    }
//    private void insert() {
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, toastAddName.getText());
//            preparedStatement.setString(2, toastAddAuthor.getText());
//            preparedStatement.setString(3, toastAddNumOfPages.getText());
//            preparedStatement.setString(4, toastAddYearOfIssue.getText());
//            preparedStatement.setString(5, toastAddChoosePicker.getValue().toString());
//            preparedStatement.execute();
//        } catch (SQLException ex) {
//            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
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

    void setUpdate(boolean b) {
        this.update = b;
    }
}
