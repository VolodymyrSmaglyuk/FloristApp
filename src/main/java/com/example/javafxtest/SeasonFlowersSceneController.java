package com.example.javafxtest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Email;
import models.Logger;
import models.SQlConnection;
import models.SeasonFlower;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeasonFlowersSceneController implements Initializable {
    @FXML
    private TableView<SeasonFlower> table;
    @FXML
    private TableColumn<SeasonFlower, Integer> bouquetId;

    @FXML
    private TableColumn<SeasonFlower, String> color;

    @FXML
    private TableColumn<SeasonFlower, String> countryOfOrigin;

    @FXML
    private TableColumn<SeasonFlower, String> flowerSeason;

    @FXML
    private TableColumn<SeasonFlower, Integer> freshnessLevel;

    @FXML
    private TableColumn<SeasonFlower, Integer> id;

    @FXML
    private TableColumn<SeasonFlower, String> name;

    @FXML
    private TableColumn<SeasonFlower, Integer> price;

    @FXML
    private TableColumn<SeasonFlower, Integer> stemLength;

    ObservableList<SeasonFlower> list =FXCollections.observableArrayList();

    @FXML
    private TextField amountOfFlowers;

    @FXML
    private TextField colorOfNewFlower;

    @FXML
    private TextField countryOfOriginOfNewFlower;

    @FXML
    private TextArea details;

    @FXML
    private TextField flowerId;

    @FXML
    private TextField flowerSeasonOfNewFlower;

    @FXML
    private TextField freshnessLevelOFNewFlower;

    @FXML
    private TextField nameOfNewFlower;

    @FXML
    private TextField priceOFNewFlower;

    @FXML
    private TextField reduceLevel;

    @FXML
    private TextField stemLengthOFNewFlower;

    static Connection connection;
    static {
        try {
            connection = SQlConnection.ConnectDB();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.logger.severe("Помилка під час підключення до бази даних"+e);
            Email.SendMessage("Помилка під час підключення до бази даних",e);
        }
    }
    @FXML
    private void refreshData(){
        list.clear();
        initList();
        table.setItems(list);
        Logger.logger.info("Оновлено дані в таблиці");
    }
    @FXML
    private void saveInFileAllFlowers(){
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\LP\\SeasonFlowerList.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fife){
            details.setText("Не вдалося відкрити файл для запису.");
            Logger.logger.severe("Не вдалося відкрити файл для запису: "+fife);
            Email.SendMessage("Не вдалося відкрити файл для запису.",fife);
            return;
        }
        for (SeasonFlower flower : list) {
            System.out.println(flower.toString());
        }
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(console);
        Logger.logger.fine("Список квітів було успішно записано в файл.");
        details.setText("");
    }
    @FXML
    private void saveInFileFlowersWithoutBouquets(){
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\LP\\SeasonFlowerList.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fife){
            details.setText("Не вдалося відкрити файл для запису.");
            Logger.logger.severe("Не вдалося відкрити файл для запису: "+fife);
            Email.SendMessage("Не вдалося відкрити файл для запису.",fife);
            return;
        }
        for (SeasonFlower flower : list) {
            if (flower.getBouquetID() == 0) {
                System.out.println(flower);
            }
        }
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(console);
        Logger.logger.fine("Список квітів було успішно записано в файл.");
        details.setText("Список квітів було успішно записано в файл.");
    }
    @FXML
    private void insertFlower() {
        int amount,freshness,fPrice,stem;
        String name,color,season,country;
        try {
            freshness = Integer.parseInt(freshnessLevelOFNewFlower.getText());
            stem = Integer.parseInt(stemLengthOFNewFlower.getText());
            fPrice = Integer.parseInt(priceOFNewFlower.getText());
            amount = Integer.parseInt(amountOfFlowers.getText());
            name = nameOfNewFlower.getText();
            color = colorOfNewFlower.getText();
            country = countryOfOriginOfNewFlower.getText();
            season = flowerSeasonOfNewFlower.getText();
        }
        catch(Exception e){
            details.setText("Помилка введення даних про квітку.");
            Logger.logger.warning("Помилка введення даних про квітку : "+e);
            return;
        }
        ArrayList<Integer> array = new ArrayList<>();
        for(;amount>0;amount--){
            int id = searchEmptyID(array);
            System.out.println(id);
            SQlConnection.insertSeasonFlower(id, freshness, stem, fPrice, name, color, season, country, connection);
            clearInput();
        }
        Logger.logger.fine("Було додано "+array.size()+" квітів.");
        details.setText("Було додано "+array.size()+" квітів.");
    }
    private int searchEmptyID(ArrayList<Integer> array){
        ArrayList<Integer> flowersId = new ArrayList<>();
        for (SeasonFlower flower : list) {
            flowersId.add(flower.getId());
        }
        flowersId.addAll(array);
        int id=1;
        while(flowersId.contains(id)){
            id++;
        }
        array.add(id);
        return id;
    }
    @FXML
    private  void clearInput(){
        flowerSeasonOfNewFlower.clear();
        countryOfOriginOfNewFlower.clear();
        nameOfNewFlower.clear();
        colorOfNewFlower.clear();
        amountOfFlowers.clear();
        stemLengthOFNewFlower.clear();
        freshnessLevelOFNewFlower.clear();
        priceOFNewFlower.clear();
        Logger.logger.info("Було очищено поля вводу.");
    }
    @FXML
    private void deleteFlower() {
        int id;
        try {
            id = Integer.parseInt(flowerId.getText());
        }
       catch (Exception e){
           details.setText("Помилка введення");
           Logger.logger.warning("Помилка введення даних :"+e);
           return;
       }
        for (SeasonFlower flower : list) {
            if (flower.getId() == id) {
                if (flower.getBouquetID() == 0) {
                    SQlConnection.deleteSeasonFlower(connection, id);
                    details.setText("Було видалено квітку квітку з id: " + id);
                    Logger.logger.fine("Було видалено квітку квітку з id: " + id);
                    flowerId.clear();
                } else {
                    Logger.logger.info("Не вдалося видалити квітку оскільки вона є в букеті.");
                    details.setText("Неможливо видалити квітку з букета.");
                }
                return;
            }
        }
        Logger.logger.info("Введено неіснуюче id квітки");
        details.setText("Такого id немає.");
    }
    @FXML
    private void updateFlower() {
        int id,reduce;
        try {
            id = Integer.parseInt(flowerId.getText());
            reduce = Integer.parseInt(reduceLevel.getText());
        }
        catch (Exception e){
            details.setText("Помилка введення.");
            Logger.logger.warning("Помилка введення: "+e);
            return;
        }
        for (SeasonFlower flower : list) {
            if (flower.getId() == id) {
                if (flower.getBouquetID() == 0) {
                    flower.reduceFreshnessLevel(reduce);
                    reduce = flower.getFreshnessLevel();
                    if(reduce ==0){
                        SQlConnection.deleteSeasonFlower(connection,id);
                        details.setText("Квітку було видалено оскільки вона зів'яла");
                        Logger.logger.info("Квітку було видалено оскільки вона зів'яла");
                        flowerId.clear();
                        reduceLevel.clear();
                        return;
                    }
                    int newPrice = flower.getPrice();
                    SQlConnection.updateSeasonFlower(id, reduce, newPrice, connection);
                    details.setText("Було модифіковано квітку.");
                    Logger.logger.fine("Було модифіковано квітку.");
                    flowerId.clear();
                    reduceLevel.clear();
                } else {
                    details.setText("Неможливо модифікувати квітку, що вже є в букеті.");
                    Logger.logger.info("Неможливо модифікувати квітку, що вже є в букеті.");
                }
                return;
            }
        }
        Logger.logger.info("Такої квітки не існує.");
        details.setText("Такої квітки не існує.");
    }
    private void initList(){
        String query = "select * from SeasonFlowers";
        try{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            SeasonFlower flower;
            flower = new SeasonFlower(resultSet.getInt("flower_ID"),
                        resultSet.getInt("FreshnessLevel"),
                        resultSet.getInt("stemLength"), resultSet.getInt("price"),
                        resultSet.getInt("bouquetID"), resultSet.getString("name"),
                        resultSet.getString("color"), resultSet.getString("flowerSeason"),
                        resultSet.getString("countryOfOrigin"));
                list.add(flower);
            }
        }
        catch(SQLException e) {
            Logger.logger.warning("Помилка під час виконання запиту до бази даних"+e);
            Email.SendMessage("Помилка під час виконання запиту до бази даних",e);
            throw new RuntimeException(e);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initList();
       id.setCellValueFactory(new PropertyValueFactory<>("id"));
       name.setCellValueFactory(new PropertyValueFactory<>("name"));
       color.setCellValueFactory(new PropertyValueFactory<>("color"));
       freshnessLevel.setCellValueFactory(new PropertyValueFactory<>("freshnessLevel"));
       stemLength.setCellValueFactory(new PropertyValueFactory<>("stemLength"));
       price.setCellValueFactory(new PropertyValueFactory<>("price"));
       flowerSeason.setCellValueFactory(new PropertyValueFactory<>("flowerSeason"));
       countryOfOrigin.setCellValueFactory(new PropertyValueFactory<>("countryOfOrigin"));
       bouquetId.setCellValueFactory(new PropertyValueFactory<>("bouquetID"));
       table.setItems(list);
       Logger.logger.info("Було ініціалізовано дані таблиці.");
    }


    @FXML
    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Logger.logger.info("Перехід в основне меню.");
        stage.setScene(scene);
        stage.show();
    }
}
