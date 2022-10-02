module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.library to javafx.fxml;
    exports com.example.library;
}