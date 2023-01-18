module com.example {

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.library to javafx.fxml;
    opens com.library.literature to javafx.base;
    opens com.library.abonents to javafx.base;
    exports com.library;

    exports com.library.service;
    opens com.library.service to javafx.fxml;

    exports com.library.controllers.mainView;
    opens com.library.controllers.mainView to javafx.fxml;

    exports com.library.controllers.users;
    opens com.library.controllers.users to javafx.fxml;

    exports com.library.controllers.messDialog;
    opens com.library.controllers.messDialog to javafx.fxml;
}