module org.example.gestioncabinet {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.jfoenix;
    requires java.persistence;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    opens Cabinet to javafx.fxml;
    opens Cabinet.models to javafx.base;
    exports Cabinet;
    exports Cabinet.controller;
    opens Cabinet.controller to javafx.fxml;
}