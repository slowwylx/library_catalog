package com.library.controllers.mainView;

import com.library.DBConnection.DBconnection;
import com.library.literature.Literature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.library.service.Global.add;
import static com.library.service.Global.selectRow;
public class AddViewController {
    @FXML
    private TextField toastAddAuthor;
    @FXML
    private ComboBox<String> toastAddChoosePicker;
    @FXML
    private Text errorTypeOfLit;
    @FXML
    private TextField toastAddName;
    @FXML
    private Button toastConfirmAddButton;
    @FXML
    private TextField toastAddNumOfPages;
    @FXML
    private TextField toastAddYearOfIssue;
    @FXML
    private Text errorAuth;

    @FXML
    private Text errorBookName;

    @FXML
    private Text errorPages;

    @FXML
    private Text errorYearIssue;

    @FXML
    void initialize() {
        loadComboBox();

            toastConfirmAddButton.setOnAction(actionEvent -> {
                save();
                if (success) {
                    toastConfirmAddButton.getScene().getWindow().hide();
                }
            });

    }

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private String rent = "1";
    private String type;
    boolean check,success = false;


    public void save() {
            String name = toastAddName.getText().trim();
            String author = toastAddAuthor.getText().trim();
            String numOfPages = toastAddNumOfPages.getText().trim();
            String yearOfIssue = toastAddYearOfIssue.getText().trim();
            type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
            if(name.isEmpty()||author.isEmpty()||numOfPages.isEmpty()||numOfPages.equals("0")||yearOfIssue.isEmpty()||type==null) {
                if (name.isEmpty()) {
                    errorBookName.setText("Fill the gaps");
                    success = false;
                } else errorBookName.setText("");
                if (author.isEmpty()) {
                    errorAuth.setText("Fill the gaps");
                    success = false;
                } else errorAuth.setText("");
                if (numOfPages.isEmpty() || numOfPages.equals("0")) {
                    errorPages.setText("Fill the gaps");
                    success = false;
                } else errorPages.setText("");
                if (yearOfIssue.isEmpty()) {
                    errorYearIssue.setText("Fill the gaps");
                    success = false;
                } else errorYearIssue.setText("");
                if(type==null){
                    errorTypeOfLit.setText("Choose type of literature");
                    success = false;
                }else errorTypeOfLit.setText("");
            }
            else {
                switch (type) {
                    case "Book" -> type = "1";
                    case "Magazine" -> type = "2";
                    case "Newspaper" -> type = "3";
                    case "Autoreferat" -> type = "4";
                }
                success = true;
                getQuery();
                insert();

            }
    }

    private void getQuery () {
            if (!add) {
                check = true;
                query = "UPDATE `bookcharacter` SET " +
                        "   `nameOfBook` = ?," +
                        "   `author` = ?," +
                        "   `pages` = ?," +
                        "   `yearOfPublish` = ?," +
                        "   `bookType` = ? WHERE (`id` = '" + selectRow.getId() + "')";
            } else {
                query = "INSERT INTO `bookcharacter`(`nameOfBook`, `author`, `pages`, `yearOfPublish`, `bookType` , `isrented`) VALUES (?,?,?,?,?,?)";
                check = false;
            }
    }
    private void insert () {
        try {
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, toastAddName.getText());
            preparedStatement.setString(2, toastAddAuthor.getText());
            preparedStatement.setString(3, toastAddNumOfPages.getText());
            preparedStatement.setString(4, toastAddYearOfIssue.getText());
            preparedStatement.setString(5, type);
            if(!check) {
                preparedStatement.setString(6, rent);
                preparedStatement.executeUpdate();
            }else {
                preparedStatement.execute();
            }
        }catch(SQLException ex){
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

   public void setTextField(int id, String name, String author, int numOfPages, int yearOfIssue) {
        int bookId = id;
        toastAddName.setText(name);
        toastAddAuthor.setText(author);
        toastAddNumOfPages.setText(String.valueOf(numOfPages));
        toastAddYearOfIssue.setText(String.valueOf(yearOfIssue));
        type = toastAddChoosePicker.getSelectionModel().getSelectedItem();
    }

    ObservableList<Literature> literatureList = FXCollections.observableArrayList();
    private void loadComboBox() {
        try {
            connection = DBconnection.getDbConnection();
            query = "SELECT * FROM library.booktype;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ObservableList<String> comboBox = FXCollections.observableArrayList();
            while (resultSet.next()) {
                literatureList.add(new Literature((resultSet.getString("bookType"))));
                comboBox.add(resultSet.getString("bookType"));
                toastAddChoosePicker.setItems(comboBox);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
