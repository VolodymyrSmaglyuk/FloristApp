package com.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Logger;

import java.io.IOException;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    @FXML
    public void switchToSeasonFlowerScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SeasonFlowersScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Logger.logger.info("Перехід до вікна сезонних квітів");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToTropicalFlowerScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("TropicalFlowersScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Logger.logger.info("Перехід до вікна тропічних квітів");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBouquetsScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BouquetsScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Logger.logger.info("Перехід до вікна букетів");
        stage.setScene(scene);
        stage.show();
    }
    public void logout(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вихід");
        alert.setHeaderText("Спроба вийти з програми.");
        alert.setContentText("Дійсно бажаєте вийти ?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Роботу завершено!");
            Logger.logger.info("Роботу програми завершено.");
            stage.close();
        }

    }
}