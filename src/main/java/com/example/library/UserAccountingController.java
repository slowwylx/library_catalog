package com.example.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.library.DBConnection.DBconnection;
import com.example.library.abonents.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.example.library.MainController.confirmDel;

public class UserAccountingController {
        @FXML
        private Button chooseUserButton;
        @FXML
        private Button deleteUserButton;
        @FXML
        private TextField searchUser;
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
        private FontAwesomeIconView addNewUser;

        @FXML
        void initialize() {
                loadDate();
                refreshTable();
                search();
                deleteUserButton.setOnAction(actionEvent -> {
                        Dlg.showWindow("delete-confirm.fxml", false,null);
                        //Dlg.showWindow("Confirm", "delete-confirm.fxml",false);
                        if(confirmDel){
                                deletion();
                        }
                });
                addNewUser.setOnMouseClicked(mouseEvent -> {
                        Dlg.showWindow("add-new-user.fxml", false,null);
                        //Dlg.showWindow("Add new user", "add-new-user.fxml", false);
                        addNewUser.getScene().getWindow().hide();
                });
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
        private void deletion(){
                try {
                        user = usersTableview.getSelectionModel().getSelectedItem();
                        query = "DELETE FROM users WHERE id  = "+user.getId();
                        connection = DBconnection.getDbConnection();
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.execute();
                        refreshTable();
                } catch (SQLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        FilteredList<User> filteredData = new FilteredList<>(userList, b->true);
        private void search(){
                searchUser.textProperty().addListener((observableValue, oldValue, newValue) ->{
                        filteredData.setPredicate(user -> {
                                if(newValue == null || newValue.isEmpty()){
                                        return true;
                                }
                                String lowerCase = newValue.toLowerCase();
                                if (user.getUserName().toLowerCase().contains(lowerCase)) {
                                        return true; // Filter matches name.
                                } else if (user.getUserSecondname().toLowerCase().contains(lowerCase)) {
                                        return true; // Filter matches author.
                                }
                                else if(user.getUserNumber().contains(lowerCase))
                                        return true;
                                else
                                        return false; // Does not match.
                        });
                });
                SortedList<User> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(usersTableview.comparatorProperty());
                usersTableview.setItems(sortedData);
        }

}

