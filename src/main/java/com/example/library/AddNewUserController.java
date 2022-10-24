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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AddNewUserController {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button confirmRegistrButton;

        @FXML
        private TextField registrNameField;

        @FXML
        private PasswordField registrPhoneField;

        @FXML
        private TextField registrSecondNameField;

        @FXML
        void initialize() {
                confirmRegistrButton.setOnAction(actionEvent -> {
                        saveUser();
                });
        }

        String query = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        User user = null;

        void saveUser() {
                try {
                        connection = DBconnection.getDbConnection();
                        String name = registrNameField.getText().trim();
                        String secondName = registrSecondNameField.getText().trim();
                        String phone = registrPhoneField.getText().trim();
                        int bookrent = 1;

                        query = "INSERT INTO `users`(`book`, `userName`, `userSecondName`, `userPhone`) VALUES (?,?,?,?)";
//fix bug with book which is rented
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, bookrent);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, secondName);
                        preparedStatement.setString(4, phone);
                        int status = preparedStatement.executeUpdate();
                } catch (
                        SQLException ex) {
                        Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
}


