package com.example.javafxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Logger;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Logger.SetupLogger();
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Flowers");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}