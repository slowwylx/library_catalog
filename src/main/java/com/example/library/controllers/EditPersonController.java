package com.example.library.controllers;

import com.example.library.DBConnection.DBconnection;
import com.example.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.library.service.Global.*;

public class EditPersonController {
    @FXML
    private TextField editAddress;

    @FXML
    private TextField editName;

    @FXML
    private TextField editNum;

    @FXML
    private TextField editSecondName;

    @FXML
    private AnchorPane toastAdd;

    @FXML
    private Button toastConfirmAddButton;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    @FXML
    void initialize() {
        setText();
        toastConfirmAddButton.setOnAction(actionEvent -> {
            getQuery();
            insert();
            if (added) {
                toastConfirmAddButton.getScene().getWindow().hide();
            }
        });

    }

    private void getQuery () {
            query = "UPDATE `users` SET " +
                    "   `userName` = ?," +
                    "   `userSecondName` = ?," +
                    "   `userPhone` = ?," +
                    "   `userAdress` = ? WHERE (`id` = '" + selectUser.getId() + "')";
    }

    private void insert () {
        try {
            String name = editName.getText();
            String secName = editSecondName.getText();
            String number = editNum.getText();
            String address = editAddress.getText();

            if (check(number) >= 1) {
                Dlg.showWindow("userexist.fxml",false,null);
            } else {
                connection = DBconnection.getDbConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, secName);
                preparedStatement.setString(3, number);
                preparedStatement.setString(4, address);
                preparedStatement.execute();
                added=true;
            }

        }catch(SQLException ex){
            Logger.getLogger(EditPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setText(){
        try{
            connection = DBconnection.getDbConnection();
            query = "SELECT * FROM library.users WHERE (`id` = '" + selectUser.getId() +"')";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                   editName.setText(resultSet.getString("userName"));
                   editSecondName.setText(resultSet.getString("userSecondName"));
                   editNum.setText(resultSet.getString("userPhone"));
                   editAddress.setText(resultSet.getString("userAdress"));
            }
        }catch(SQLException ex){
                Logger.getLogger(EditPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
