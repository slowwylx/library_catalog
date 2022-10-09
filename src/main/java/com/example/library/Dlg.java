package com.example.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Dlg {
    public static void showWindow(String title, String fxmlName, Boolean myScale){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Dlg.class.getResource("" +fxmlName));
            Parent fxmlContent = fxmlLoader.load();
            Stage secondWindow = new Stage();
            secondWindow.setTitle(title);
            secondWindow.setScene(new Scene(fxmlContent));
            secondWindow.initModality(Modality.APPLICATION_MODAL);
            secondWindow.setResizable(myScale);
            secondWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
