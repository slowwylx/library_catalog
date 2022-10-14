module com.example.library {

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.library to javafx.fxml;
    opens com.example.library.literature to javafx.base;
    exports com.example.library;
}