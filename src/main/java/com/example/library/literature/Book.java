package com.example.library.literature;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;

public class Book {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty yearOfissue;
    private IntegerProperty pages;
    private BooleanProperty isAvailable;
    private StringProperty author;

    public Book(int id, String name, int yearOfissue, int pages, String author,  Boolean isAvailable) {
        this.id=new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.yearOfissue = new SimpleIntegerProperty(yearOfissue);
        this.pages = new SimpleIntegerProperty(pages);
        this.isAvailable = new SimpleBooleanProperty(isAvailable);
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

    public boolean isIsAvailable() {
        return isAvailable.get();
    }

    public BooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
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
