package com.library.abonents;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty userName;
    private StringProperty userSecondname;
    private StringProperty userNumber;
    private IntegerProperty userRentedBook;
    private IntegerProperty id;
    private StringProperty address;

    public User(String userName, String userSecondname, String userNumber, int userRentedBook, int id,String address) {
        this.userName =new SimpleStringProperty(userName);
        this.userSecondname =new SimpleStringProperty(userSecondname);
        this.userNumber = new SimpleStringProperty(userNumber);
        this.userRentedBook = new SimpleIntegerProperty(userRentedBook);
        this.id = new SimpleIntegerProperty(id);
        this.address = new SimpleStringProperty(address);
    }

    public User() {

    }

    public User(String userName, String userSecondName, String userPhone, int id) {
        this.userName =new SimpleStringProperty(userName);
        this.userSecondname =new SimpleStringProperty(userSecondName);
        this.userNumber = new SimpleStringProperty(userPhone);
        this.id = new SimpleIntegerProperty(id);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserSecondname() {
        return userSecondname.get();
    }

    public StringProperty userSecondnameProperty() {
        return userSecondname;
    }

    public void setUserSecondname(String userSecondname) {
        this.userSecondname.set(userSecondname);
    }

    public String getUserNumber() {
        return userNumber.get();
    }

    public StringProperty userNumberProperty() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber.set(userNumber);
    }

    public int getUserRentedBook() {
        return userRentedBook.get();
    }

    public IntegerProperty userRentedBookProperty() {
        return userRentedBook;
    }

    public void setUserRentedBook(int userRentedBook) {
        this.userRentedBook.set(userRentedBook);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
