package com.example.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class Dlg {
    public static <T> T showWindow(String fxmlName, Boolean myScale, Consumer<T> beforeShow) {
        final var loader = new FXMLLoader(Dlg.class.getResource("" + fxmlName));
        try {
            Parent parent = loader.load();
            T controller = loader.getController();
            if(beforeShow!=null) {
                beforeShow.accept(controller);
            }
            final var stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(myScale);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
