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
import models.TropicalFlower;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TropicalFlowerSceneController implements Initializable {
    @FXML
    private TableColumn<TropicalFlower, Integer> bouquetID;

    @FXML
    private TableColumn<TropicalFlower, String> color;

    @FXML
    private TableColumn<TropicalFlower, Integer> compatibleWithOthers;

    @FXML
    private TableColumn<TropicalFlower, String> countryOfOrigin;

    @FXML
    private TableColumn<TropicalFlower, Integer> freshness;

    @FXML
    private TableColumn<TropicalFlower, Integer> id;

    @FXML
    private TableColumn<TropicalFlower, String> name;

    @FXML
    private TableColumn<TropicalFlower, Integer> price;

    @FXML
    private TableColumn<TropicalFlower, Integer> stemLength;
    @FXML
    private TableView<TropicalFlower> table;

    ObservableList<TropicalFlower> list = FXCollections.observableArrayList();

    @FXML
    private TextArea consoleText;

    @FXML
    private TextField amountOfFlowers;

    @FXML
    private TextField colorOfNewFlower;

    @FXML
    private TextField compatibilityOfNewFlower;

    @FXML
    private TextField countryOfOriginOfNewFlower;

    @FXML
    private TextField flowerId;

    @FXML
    private TextField freshnessOfNewFlower;

    @FXML
    private TextField nameOfNewFlower;

    @FXML
    private TextField priceOfNewFlower;

    @FXML
    private TextField reduceLevel;

    @FXML
    private TextField stemLengthOfNewFlower;

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
    private void saveInFileAllFlowers(){
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\LP\\TropicalFlowerList.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fife){
            consoleText.setText("Не вдалося відкрити файл для запису.");
            Logger.logger.severe("Не вдалося відкрити файл для запису: "+fife);
            Email.SendMessage("Не вдалося відкрити файл для запису.",fife);
            return;
        }
        for (TropicalFlower flower : list) {
            System.out.println(flower.toString());
        }
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(console);
        Logger.logger.fine("Список квітів було успішно записано в файл.");
        consoleText.setText("Список квітів було успішно записано в файл.");
    }
    @FXML
    private void saveInFileFlowersWithoutBouquets(){
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\LP\\TropicalFlowerList.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fife){
            consoleText.setText("Не вдалося відкрити файл для запису.");
            Logger.logger.severe("Не вдалося відкрити файл для запису: "+fife);
            Email.SendMessage("Не вдалося відкрити файл для запису.",fife);
            return;
        }
        for (TropicalFlower flower : list) {
            if (flower.getBouquetID() == 0) {
                System.out.println(flower);
            }
        }
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(console);
        Logger.logger.fine("Список квітів було успішно записано в файл.");
        consoleText.setText("Список квітів було успішно записано в файл.");
    }
    @FXML
    private void insertFlower() {
        int amount,freshness,fPrice,stem;
        String name,color,country;
        try {
            freshness = Integer.parseInt(freshnessOfNewFlower.getText());
            stem = Integer.parseInt(stemLengthOfNewFlower.getText());
            fPrice = Integer.parseInt(priceOfNewFlower.getText());
            amount = Integer.parseInt(amountOfFlowers.getText());
            name = nameOfNewFlower.getText();
            color=colorOfNewFlower.getText();
            country=countryOfOriginOfNewFlower.getText();
        }
        catch(Exception e){
            consoleText.setText("Помилка введення даних про квітку.");
            Logger.logger.warning("Помилка введення даних про квітку.");
            return;
        }
        ArrayList<Integer> array = new ArrayList<>();
        for(;amount>0;amount--){
            int id = searchEmptyID(array);
            int compatibility=0;
            if(compatibilityOfNewFlower.getText().toLowerCase().contains("yes")||
                    compatibilityOfNewFlower.getText().toLowerCase().contains("true")||
                    compatibilityOfNewFlower.getText().toLowerCase().contains("так")){
                compatibility=1;
            }
            SQlConnection.InsertTropicalFlower(id, freshness, stem, fPrice, name, color, compatibility, country,
                    connection);
            clearInput();
        }
        consoleText.setText("Було додано "+array.size()+" квітів.");
        Logger.logger.fine("Було додано "+array.size()+" квітів.");

    }
    private int searchEmptyID(ArrayList <Integer> arrayList){
        ArrayList<Integer> flowersId = new ArrayList<>();
        for (TropicalFlower flower : list) {
            flowersId.add(flower.getId());
        }
        flowersId.addAll(arrayList);
        int id=1;
        while(flowersId.contains(id)){
            id++;
        }
        arrayList.add(id);
        return id;
    }
    @FXML
    private  void clearInput(){
        freshnessOfNewFlower.clear();
        countryOfOriginOfNewFlower.clear();
        nameOfNewFlower.clear();
        colorOfNewFlower.clear();
        amountOfFlowers.clear();
        stemLengthOfNewFlower.clear();
        compatibilityOfNewFlower.clear();
        priceOfNewFlower.clear();
        Logger.logger.info("Було очищено поля вводу.");
    }
    @FXML
    private void deleteFlower() {
        int id;
        try {
            id = Integer.parseInt(flowerId.getText());
        }
        catch (Exception e){
            consoleText.setText("Помилка введення");
            Logger.logger.warning("Помилка введення"+e);
            return;
        }
        for (TropicalFlower flower : list) {
            if (flower.getId() == id) {
                if (flower.getBouquetID() == 0) {
                    SQlConnection.deleteTropicalFlower(connection,id);
                    consoleText.setText("Було видалено квітку квітку з id: " + id);
                    Logger.logger.info("Було видалено квітку квітку з id: " + id);
                    flowerId.clear();
                } else {
                    Logger.logger.info("Неможливо видалити квітку з букета.");
                    consoleText.setText("Неможливо видалити квітку з букета.");
                }
                return;
            }
        }
        Logger.logger.info("Такого id немає.");
        consoleText.setText("Такого id немає.");
    }
    @FXML
    private void updateFlower() {
        int id,reduce;
        try {
            id = Integer.parseInt(flowerId.getText());
            reduce = Integer.parseInt(reduceLevel.getText());
        }
        catch (Exception e){
            consoleText.setText("Помилка введення.");
            Logger.logger.warning("Помилка введення."+e);
            return;
        }
        for (TropicalFlower flower : list) {
            if (flower.getId() == id) {
                if (flower.getBouquetID() == 0) {
                    flower.reduceFreshnessLevel(reduce);
                    reduce = flower.getFreshnessLevel();
                    if(reduce ==0){
                        SQlConnection.deleteTropicalFlower(connection,id);
                        consoleText.setText("Квітку було видалено оскільки вона зів'яла");
                        Logger.logger.info("Квітку було видалено оскільки вона зів'яла");
                        flowerId.clear();
                        reduceLevel.clear();
                        return;
                    }
                    int newPrice = flower.getPrice();
                    SQlConnection.updateTropicalFlower(connection,id,newPrice,reduce);
                    consoleText.setText("Було модифіковано квітку.");
                    Logger.logger.fine("Було модифіковано квітку.");
                    flowerId.clear();
                    reduceLevel.clear();
                } else {
                    consoleText.setText("Неможливо модифікувати квітку, що вже є в букеті.");
                    Logger.logger.info("Неможливо модифікувати квітку, що вже є в букеті.");
                }
                return;
            }
        }
        Logger.logger.info("Такої квітки не існує.");
        consoleText.setText("Такої квітки не існує.");
    }
    @FXML
    private void refreshData(){
        list.clear();
        initList();
        table.setItems(list);
    }

    private void initList(){
        String query = "select * from TropicalFlowers";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                TropicalFlower flower;
                flower = new TropicalFlower(resultSet.getInt("flower_ID"),
                        resultSet.getString("name"), resultSet.getString("color"),
                        resultSet.getInt("FreshnessLevel"),resultSet.getInt("stemLength"),
                        resultSet.getBoolean("compatibleWithOthers"),
                        resultSet.getString("countryOfOrigin"),resultSet.getInt("price"),
                        resultSet.getInt("bouquetID"));
                list.add(flower);
            }
        }
        catch(SQLException e) {
            Logger.logger.severe("Помилка під час виконання запиту :" +e);
            Email.SendMessage("Помилка під час виконання запиту :",e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initList();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        freshness.setCellValueFactory(new PropertyValueFactory<>("freshnessLevel"));
        stemLength.setCellValueFactory(new PropertyValueFactory<>("stemLength"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        compatibleWithOthers.setCellValueFactory(new PropertyValueFactory<>("compatibleWithOthers"));
        countryOfOrigin.setCellValueFactory(new PropertyValueFactory<>("countryOfOrigin"));
        bouquetID.setCellValueFactory(new PropertyValueFactory<>("bouquetID"));
        table.setItems(list);
        Logger.logger.info("Було ініціалізовано таблицю.");
    }
    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Logger.logger.info("Перехід до основного меню.");
        stage.setScene(scene);
        stage.show();
    }

}
