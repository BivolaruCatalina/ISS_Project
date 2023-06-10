module com.example.dailytasks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
 //   requires java.persistence;
    requires java.sql;

    opens com.example.dailytasks2 to javafx.fxml;
    opens com.example.dailytasks2.controller to javafx.fxml;

    exports com.example.dailytasks2;

    exports com.example.dailytasks2.controller;
    exports com.example.dailytasks2.service;
    exports com.example.dailytasks2.domain;
    exports com.example.dailytasks2.utils;
}