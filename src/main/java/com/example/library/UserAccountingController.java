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
import com.example.library.abonents.User;
import com.example.library.literature.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.example.library.DBConnection.Const.*;
import static com.example.library.DBConnection.Const.LITERATURE_AVAILABILITY;

public class UserAccountingController {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button chooseUserButton;

        @FXML
        private Button deleteUserButton;

        @FXML
        private TextField searchUser;

        @FXML
        private AnchorPane userAccANCHORpane;

        @FXML
        private Pane userAccANCHORpaneHEADER;

        @FXML
        private Text userAccANCHORpaneHEADtext;

        @FXML
        private TableColumn<User, Integer> userPhoneCol;

        @FXML
        private TableColumn<User, String> userSecondNameCol;
        @FXML
        private TableColumn<User, String> userRentColumn;//or Book
        @FXML
        private TableColumn<User, String> usernameCol;

        @FXML
        private TableView<User> usersTableview;

        @FXML
        void initialize() {
                loadDate();
                refreshTable();
        }

        String query = null;
        Connection connection = null ;
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet = null ;
        User user = null ;

        ObservableList<User> userList = FXCollections.observableArrayList();

        private void loadDate() {

                        connection = DBconnection.getDbConnection();

                        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
                        userSecondNameCol.setCellValueFactory(new PropertyValueFactory<>("userSecondname"));
                        userPhoneCol.setCellValueFactory(new PropertyValueFactory<>("userNumber"));
                        userRentColumn.setCellValueFactory(new PropertyValueFactory<>("userRentedBook"));


        }
        private void refreshTable() {
                try {
                        userList.clear();
                        query = "SELECT * FROM library.users;";
                        preparedStatement = connection.prepareStatement(query);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){ userList.add(new User(
                                resultSet.getString("userName"),
                                resultSet.getString("userSecondName"),
                                resultSet.getString("userPhone"),
                                resultSet.getInt("book"),
                                resultSet.getInt("id")));
                                usersTableview.setItems(userList);
                        }
                } catch (SQLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

}

