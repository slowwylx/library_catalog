package com.example.library.controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.library.DBConnection.DBconnection;
import com.example.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.library.service.Global.*;

public class AddNewUserController {
        @FXML
        private Button confirmRegistrButton;
        @FXML
        private TextField registrNameField;
        @FXML
        private PasswordField registrPhoneField;
        @FXML
        private TextField registrSecondNameField;
        @FXML
        private Text secondNameErrField;
        @FXML
        private Text nameErrField;
        @FXML
        private Text phoneErrField;

        private ResultSet resultSet;
        private static String phn2 = null;

        @FXML
        void initialize() {
                confirmRegistrButton.setOnAction(actionEvent -> {
                        saveUser();
                        if(added){
                                confirmRegistrButton.getScene().getWindow().hide();
                        }
                });
        }
      //  String phNumber;
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement;


        public void saveUser(){
                try {
                        phn2 = registrPhoneField.getText();
                        if(check(phn2)>=1){
                                Dlg.showWindow("userexist.fxml",false,null);

                        }else {
                                connection = DBconnection.getDbConnection();
                                String name = registrNameField.getText().trim();
                                String secondName = registrSecondNameField.getText().trim();
                                String phone = registrPhoneField.getText().trim();
                                String bookrent = null;
                                String address = null;
                                if(name.isEmpty() || secondName.isEmpty()|| phone.length()<9 || phone.length()>13){
                                              nameErrField.setText("Fill the gaps");
                                              secondNameErrField.setText("Fill the gaps");
                                              nameErrField.setText("");
                                              secondNameErrField.setText("");
                                              phoneErrField.setText("");
                                              phoneErrField.setText("Enter correct number!");
                                }
                                else {
                                        query = "INSERT INTO `users`(`book`, `userName`, `userSecondName`, `userPhone`,`userAdress` ) VALUES (?,?,?,?,?)";
                                        //fix bug with book which is rented
                                        preparedStatement = connection.prepareStatement(query);
                                        preparedStatement.setString(1, bookrent);
                                        preparedStatement.setString(2, name);
                                        preparedStatement.setString(3, secondName);
                                        preparedStatement.setString(4, phone);
                                        preparedStatement.setString(5, address);
                                        preparedStatement.executeUpdate();
                                        added = true;
                                }
                        }
                } catch ( SQLException ex) {
                        Logger.getLogger(AddNewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }


}


