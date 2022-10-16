package com.example.library.literature;
import com.example.library.DBConnection.DBconnection;
import com.example.library.MainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.library.DBConnection.Const.LITERATURE_AVAILABILITY;

public class Book {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty yearOfissue;
    private IntegerProperty pages;
    private StringProperty isAvailable;
    private StringProperty author;
    private IntegerProperty idStatus;

    public Book(int id, String name, int yearOfissue, int pages, String author,  String isAvailable) {
        this.id=new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.yearOfissue = new SimpleIntegerProperty(yearOfissue);
        this.pages = new SimpleIntegerProperty(pages);
        this.isAvailable = new SimpleStringProperty(isAvailable);
        this.author = new SimpleStringProperty(author);
    }

    public Book(){

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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getYearOfissue() {
        return yearOfissue.get();
    }

    public IntegerProperty yearOfissueProperty() {
        return yearOfissue;
    }

    public void setYearOfissue(int yearOfissue) {
        this.yearOfissue.set(yearOfissue);
    }

    public int getPages() {
        return pages.get();
    }

    public IntegerProperty pagesProperty() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.set(pages);
    }

    public String getIsAvailable() {
        return isAvailable.get();
    }

    public StringProperty isAvailableProperty() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable.set(isAvailable);
    }

    public int getIdStatus() {
        return idStatus.get();
    }

    public IntegerProperty idStatusProperty() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus.set(idStatus);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }
}
