package com.example.library.controllers;

import com.example.library.DBConnection.DBconnection;
import com.example.library.Dlg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.library.service.Global.*;

public class EditPersonController {
    @FXML
    private Text errorAddress;

    @FXML
    private Text errorName;

    @FXML
    private Text errorPhone;

    @FXML
    private Text errorSecName;
    @FXML
    private TextField editAddress;

    @FXML
    private TextField editName;

    @FXML
    private TextField editNum;

    @FXML
    private TextField editSecondName;

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
            if(check(editNum.getText())>=1){
                errorPhone.setText("Already registered!");
            }else{
                getQuery();
                insert();
                if (added) {
                    toastConfirmAddButton.getScene().getWindow().hide();
                }
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
            if(name.isEmpty()||secName.isEmpty()||number.isEmpty()||address.isEmpty()){
                errorName.setText("Fill the gaps");
                errorSecName.setText("Fill the gaps");
                errorPhone.setText("Fill the gaps");
                errorAddress.setText("Fill the gaps");
            }else {
                connection = DBconnection.getDbConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, secName);
                preparedStatement.setString(3, number);
                preparedStatement.setString(4, address);
                boolean status = preparedStatement.execute();
                if (!status) {
                    added = true;
                }
            }
        }catch(SQLException ex){
            Logger.getLogger(EditPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
