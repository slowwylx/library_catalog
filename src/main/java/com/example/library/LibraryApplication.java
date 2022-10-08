package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("JustLibrary!");
        stage.setScene(scene);
        stage.show();
        //stage.initStyle(StageStyle.TRANSPARENT);
    }

    public static void main(String[] args) {
        launch();
    }
}