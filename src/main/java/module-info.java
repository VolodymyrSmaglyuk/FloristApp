module com.example.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens com.example.javafxtest to javafx.fxml;
    exports com.example.javafxtest;
    exports models;
    opens models to javafx.fxml;
}