package com.library.controllers.users;

import com.library.DBConnection.DBconnection;
import com.library.literature.Book;
import com.library.Dlg;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.library.DBConnection.Const.*;
import static com.library.service.Global.selectRow;
import static com.library.service.Global.selectUser;


public class AbonentController{
    @FXML
    private Label adressLabel;

    @FXML
    private FontAwesomeIconView editUserButton;

    @FXML
    private Button giveBackButton;
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
    private Book selectedBookInUser;
    @FXML
    void initialize() {

        setLabelField();
        loadDate();
        refreshTable();

        editUserButton.setOnMouseClicked(mouseEvent -> {
           Dlg.showWindow("editPerson.fxml", false,null);

        });
        giveOutButton.setOnAction(actionEvent -> {
            reestrSet();
            giveOutButton.getScene().getWindow().hide();
        });

        giveBackButton.setOnAction(actionEvent -> {

            selectedBookInUser = userLitTable.getSelectionModel().getSelectedItem();
            delete();
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
    private void reestrSet(){
        boolean isDel;
        if(alreadyRented()>=1){
            Dlg.showWindow("already-rented.fxml",false,null);
            isDel=false;
            setRentTrue(isDel);
        }else {
            try {
                connection = DBconnection.getDbConnection();
                query = "INSERT INTO reestr (`book`, `renterer`) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(selectRow.getId()));
                preparedStatement.setString(2, String.valueOf(selectUser.getId()));
                boolean failure = preparedStatement.execute();
                if (!failure) {
                    isDel=false;
                    setRentTrue(isDel);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void loadDate() {
        try {
            connection = DBconnection.getDbConnection();
            refreshTable();
            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
            tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            yearOfPublishCol.setCellValueFactory(new PropertyValueFactory<>("yearOfissue"));
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private final ObservableList<Book> userRentList = FXCollections.observableArrayList();
    private void refreshTable() {
        try {
            connection = DBconnection.getDbConnection();
            userRentList.clear();
            query = "SELECT bookcharacter.id,bookcharacter.nameOfBook,bookcharacter.pages,bookcharacter.yearOfPublish,bookcharacter.author " +
                    "FROM reestr " +
                    "INNER JOIN users " +
                    "ON users.id=reestr.renterer " +
                    "INNER JOIN bookcharacter " +
                    "ON bookcharacter.id=reestr.book " +
                    "WHERE (users.id='"+selectUser.getId()+"')";
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
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void setRentTrue(Boolean isDel){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            if(isDel==false) {
                query = "UPDATE bookcharacter SET isrented=? WHERE id = '" + selectRow.getId() + "'";
            }else {
                query = "UPDATE bookcharacter SET isrented=? WHERE id = '" + selectedBookInUser.getId() + "'";
            }
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            if(isDel==false) {
                preparedStatement.setString(1,"0");
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
            query = "DELETE FROM reestr WHERE book = " + book.getId();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            boolean deleted = true;
            setRentTrue(deleted);
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int alreadyRented(){
        int isRented = 0;
        try {
            query = "SELECT reestr.book FROM reestr where book = "+selectRow.getId();
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                isRented++;
            }
        }catch (SQLException ex){
            Logger.getLogger(AbonentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isRented;
    }

}
