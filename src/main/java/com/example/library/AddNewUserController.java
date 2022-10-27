package com.example.library;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.library.DBConnection.DBconnection;
import com.example.library.abonents.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddNewUserController {
        @FXML
        private Button confirmRegistrButton;
        @FXML
        private TextField registrNameField;
        @FXML
        private PasswordField registrPhoneField;
        @FXML
        private TextField registrSecondNameField;

        private ResultSet resultSet;

        @FXML
        void initialize() {
                confirmRegistrButton.setOnAction(actionEvent -> {
                        saveUser();
                        confirmRegistrButton.getScene().getWindow().hide();
                });
        }
      //  String phNumber;
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement;

        public void saveUser(){
                try {
                        connection = DBconnection.getDbConnection();
                        String name = registrNameField.getText().trim();
                        String secondName = registrSecondNameField.getText().trim();
                        String phone = registrPhoneField.getText().trim();
                        String bookrent = null;
                        query = "INSERT INTO `users`(`book`, `userName`, `userSecondName`, `userPhone`) VALUES (?,?,?,?)";
                        //fix bug with book which is rented
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, bookrent);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, secondName);
                        preparedStatement.setString(4, phone);
                        preparedStatement.executeUpdate();
                } catch (
                        SQLException ex) {
                        Logger.getLogger(AddNewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }


}


