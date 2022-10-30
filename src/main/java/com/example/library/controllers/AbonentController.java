package com.example.library.controllers;

import com.example.library.DBConnection.DBconnection;
import com.example.library.Dlg;
import com.example.library.literature.Book;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.example.library.DBConnection.Const.*;
import static com.example.library.DBConnection.Const.LITERATURE_AVAILABILITY;
import static com.example.library.service.Global.*;


public class AbonentController {
    @FXML
    private Label adressLabel;

    @FXML
    private FontAwesomeIconView editUserButton;

    @FXML
    private Button giveBackButton;

    @FXML
    private GridPane gridPaneId;


    @FXML
    private Text headerText;
    @FXML
    private Button giveOutButton;
    @FXML
    private TableView<Book> userLitTable;
    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Book, Integer> pagesCol;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label secondNameLabel;

    @FXML
    private TableColumn<Book, String> tableAuthor;

    @FXML
    private TableColumn<Book, String> tableName;

    @FXML
    private TableColumn<Book, Integer> yearOfPublishCol;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;


    private Book book;
    @FXML
    void initialize() {
        setLabelField();

        editUserButton.setOnMouseClicked(mouseEvent -> {
           Dlg.showWindow("editPerson.fxml", false,null);

        });
        giveOutButton.setOnAction(actionEvent -> {
            giveOut=false;
            reestrSet();
            giveOutButton.getScene().getWindow().hide();
        });
        loadDate();
        refreshTable();
        giveBackButton.setOnAction(actionEvent -> {
            giveOut=true;
            delete();
            refreshTable();
        });

    }
    private void setLabelField() {
        try{
            connection = DBconnection.getDbConnection();
            query = "SELECT * FROM library.users WHERE (`id` = '" + selectUser.getId() +"')";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                nameLabel.setText(resultSet.getString("userName"));
                secondNameLabel.setText(resultSet.getString("userSecondName"));
                phoneLabel.setText(resultSet.getString("userPhone"));
                adressLabel.setText(resultSet.getString("userAdress"));
            }
        }catch(SQLException ex){
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void reestrSet(){
        try{
            connection = DBconnection.getDbConnection();
            query = "INSERT INTO reestr (`book`, `renterer`) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(selectRow.getId()));
            preparedStatement.setString(2, String.valueOf(selectUser.getId()));
            boolean success = preparedStatement.execute();
            if(!success){
                setRentTrue();
            }
        }catch(SQLException ex){
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadDate() {
        try {
            connection = DBconnection.getDbConnection();
            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
            tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            yearOfPublishCol.setCellValueFactory(new PropertyValueFactory<>("yearOfissue"));
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private final ObservableList<Book> userRentList = FXCollections.observableArrayList();
    private void refreshTable() {
        try {
            connection = DBconnection.getDbConnection();
            userRentList.clear();
            query = "SELECT reestr.id,bookcharacter.nameOfBook,bookcharacter.pages,bookcharacter.yearOfPublish,bookcharacter.author FROM reestr INNER JOIN users ON users.id=reestr.renterer INNER JOIN bookcharacter ON bookcharacter.id=reestr.book WHERE (users.id='"+selectUser.getId()+"')";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userRentList.add(new Book(
                        resultSet.getInt(LITERATURE_ID),
                        resultSet.getString(LITERATURE_NAME),
                        resultSet.getInt(LITERATURE_PAGES),
                        resultSet.getInt(LITERATURE_YEAR),
                        resultSet.getString(LITERATURE_AUTHOR)
                ));
                userLitTable.setItems(userRentList);
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
    public static void setRentTrue(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryStatus = "UPDATE bookcharacter SET isrented=? WHERE id = '"+selectRow.getId()+"'";
        try{
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(queryStatus);
            if(giveOut==false) {
                preparedStatement.setString(1, "0");
            }else {
                preparedStatement.setString(1,"1");
            }
            preparedStatement.execute();
        }catch(SQLException ex){
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void delete(){
        try {
            connection = DBconnection.getDbConnection();
            book = userLitTable.getSelectionModel().getSelectedItem();
            query = "DELETE FROM reestr WHERE id  = " + book.getId();
            preparedStatement = connection.prepareStatement(query);
           boolean success = preparedStatement.execute();
           if(!success){
               setRentTrue();
           }
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

}
