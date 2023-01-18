package com.library.controllers.users;

import com.library.DBConnection.DBconnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.library.service.Global.*;

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
            if(name.isEmpty()||secName.isEmpty()||number.length()<10||address.isEmpty()){
                    if (name.isEmpty()) {
                        errorName.setText("Fill the gaps");
                    } else errorName.setText("");
                    if (secName.isEmpty()) {
                        errorSecName.setText("Fill the gaps");
                    } else errorSecName.setText("");
                    if (!number.matches("\\d{10}|\\d{12}")) {
                        errorPhone.setText("Number must be 10 digit or 12(with country code)");
                    } else errorPhone.setText("");
                    if (address.isEmpty()) {
                        errorAddress.setText("Fill the gaps");
                    } else errorAddress.setText("");
                }
            else {
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
