package com.example.library.literature;

import javafx.beans.property.*;

public class Book {
    private StringProperty name;
    private StringProperty yearOfissue;
    private IntegerProperty pages;
    private BooleanProperty isAvailable;
    private StringProperty author;

    public Book(String name, String yearOfissue, int pages, Boolean isAvailable, String author) {
        this.name = new SimpleStringProperty(name);
        this.yearOfissue = new SimpleStringProperty(yearOfissue);
        this.pages = new SimpleIntegerProperty(pages);
        this.isAvailable = new SimpleBooleanProperty(isAvailable);
        this.author = new SimpleStringProperty(author);
    }

    public Book(){
        name = new SimpleStringProperty();
        yearOfissue = null;
        pages = null;
        isAvailable = null;
        author = null;
    }
}
