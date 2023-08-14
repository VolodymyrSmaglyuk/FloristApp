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
import models.*;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BouquetsController implements Initializable {
    @FXML
    private TextField FlowersIdString;

    @FXML
    private TextField FlowersInterval;

    @FXML
    private TableColumn<Bouquet, Integer> bouquetDecorPrice;

    @FXML
    private TableColumn<Bouquet, String> bouquetExtraElements;

    @FXML
    private TableColumn<Bouquet, Integer> bouquetId;

    @FXML
    private TableColumn<Bouquet, Integer> bouquetPrice;

    @FXML
    private TableColumn<Bouquet, String> bouquetWrapper;

    @FXML
    private TableView<Bouquet> bouquetTable;


    ObservableList<Bouquet> bouquets = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Flower, String> flowerColor;

    @FXML
    private TableColumn<Flower, Integer> flowerCompatibility;

    @FXML
    private TableColumn<Flower, String> flowerCountryOfOrigin;

    @FXML
    private TableColumn<Flower, Integer> flowerFreshness;

    @FXML
    private TableColumn<Flower, Integer> flowerId;

    @FXML
    private TableColumn<Flower, String> flowerName;

    @FXML
    private TableColumn<Flower, Integer> flowerPrice;

    @FXML
    private TableColumn<Flower, Integer> flowerStemLength;

    @FXML
    private TableView<Flower> flowerTable;

    ObservableList<Flower> flowers = FXCollections.observableArrayList();

    @FXML
    private TextField reduceLevel;

    @FXML
    private TextField bouquetIdText;

    @FXML
    private TextField decorPriceOfNewBouquet;

    @FXML
    private TextField extraElementsOfNewBouquet;

    @FXML
    private TextField wrapperOfNewBouquet;

    @FXML
    private  TextField typeOfFlower;

    @FXML
    private TextField searchId;

    @FXML
    private TextArea console;

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
    private  void printSeasonFLowersWithoutBouquet(){
        initSeasonFlowersList(0);
        setFlowerTableColumns();
        Logger.logger.fine("Було виведено сезонні квіти без букетів");
    }
    @FXML
    private void refreshBouquetsData(){
        bouquets.clear();
        initBouquetsList();
        bouquetTable.setItems(bouquets);
    }
    private void setFlowerTableColumns(){
        flowerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        flowerColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        flowerFreshness.setCellValueFactory(new PropertyValueFactory<>("freshnessLevel"));
        flowerStemLength.setCellValueFactory(new PropertyValueFactory<>("stemLength"));
        flowerPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        flowerCountryOfOrigin.setCellValueFactory(new PropertyValueFactory<>("countryOfOrigin"));
        flowerCompatibility.setCellValueFactory(new PropertyValueFactory<>("compatibility"));
        flowerTable.setItems(flowers);
        Logger.logger.info("Було ініціалізовано таблицю квітів.");
    }
    @FXML
    private void printTropicalFlowersWithoutBouquet(){
        initTropicalFlowersList(0);
        setFlowerTableColumns();
        Logger.logger.fine("Було виведено тропічні квіти без букетів");
    }
    @FXML
    private void printBouquetFlowersWithId(){
        int id;
        try {
            id = Integer.parseInt(searchId.getText());
        }
        catch (Exception e){
            console.setText("Помилка введення id.");
            Logger.logger.warning("Помилка введення id."+e);
            return;
        }
        for(Bouquet bouquet:bouquets){
            if(bouquet.getId()==id){
                initSeasonFlowersList(id);
                if(flowers.size()==0) {
                    initTropicalFlowersList(id);
                }
                setFlowerTableColumns();
                return;
            }
        }
        Logger.logger.info("Букета з таким id не існує");
        console.setText("Букета з таким id не існує");
    }
    @FXML
    private void clearInput(){
        wrapperOfNewBouquet.clear();
        extraElementsOfNewBouquet.clear();
        decorPriceOfNewBouquet.clear();
        FlowersIdString.clear();
        FlowersInterval.clear();
        typeOfFlower.clear();
        Logger.logger.info("Було очищено поля вводу.");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBouquetsList();
        bouquetId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bouquetPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bouquetWrapper.setCellValueFactory(new PropertyValueFactory<>("wrapperType"));
        bouquetExtraElements.setCellValueFactory(new PropertyValueFactory<>("extraElements"));
        bouquetDecorPrice.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        bouquetTable.setItems(bouquets);
        Logger.logger.info("Було ініціалізовано таблицю букетів.");
    }
    @FXML
    private void switchToMainScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Logger.logger.info("Перехід в основне меню.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void createBouquetFromInterval() {
        if(FlowersInterval.getText().isEmpty()){
            console.setText("Поле інтервалу порожнє.");
            return;
        }
        int[] array;
        int decorPrice;
        try{
            array = Array.convertStringIntoArray(FlowersInterval.getText());
            decorPrice = Integer.parseInt(decorPriceOfNewBouquet.getText());
        }
        catch (Exception e){
            Logger.logger.warning("Неправильне введення."+e);
            console.setText("Неправильне введення.");
            return;
        }
        if(array.length>2){
            Logger.logger.info("Помилка введення інтервалу.");
            console.setText("Помилка введення інтервалу.");
            return;
        }
        int id = searchEmptyID();
        if(typeOfFlower.getText().equals("SeasonFlower")) {
            ArrayList<SeasonFlower>seasonFlowers=
                    SQlConnection.selectSeasonFlowersOnInterval(connection,array[0],array[1]);
            if(Compatibility.checkConditionsOfCreatingSeasonBouquet(seasonFlowers)) {
                int totalPrice = decorPrice;
                for(SeasonFlower flower:seasonFlowers){
                    totalPrice+=flower.getPrice();
                }
                SQlConnection.createSeasonFlowersBouquetFromInterval(totalPrice,id,wrapperOfNewBouquet.getText(),
                        decorPrice,
                        extraElementsOfNewBouquet.getText(), array[0], array[1], connection);
                Logger.logger.fine("Сезонний букет було створено.");
                console.setText("Букет було створено.");
            }
            else{
                Logger.logger.info("Не вдалося створити букет оскількм квіти не сумісні.");
                console.setText("Не вдалося створити букет оскількм квіти не сумісні.");
            }
        } else if (typeOfFlower.getText().equals("TropicalFlower")) {
            ArrayList<TropicalFlower>tropicalFlowers=
                    SQlConnection.selectTropicalFlowersOnInterval(connection,array[0],array[1]);
            if(Compatibility.checkConditionsOfCreatingTropicalBouquet(tropicalFlowers)){
                int totalPrice = decorPrice;
                for(TropicalFlower flower:tropicalFlowers){
                    totalPrice+=flower.getPrice();
                }
                SQlConnection.createTropicalFlowersBouquetFromInterval(totalPrice,id,wrapperOfNewBouquet.getText(),
                        decorPrice,
                        extraElementsOfNewBouquet.getText(), array[0], array[1], connection);
                console.setText("Букет було створено.");
                Logger.logger.fine("Тропічний букет було створено.");
                clearInput();
            }
            else {
                Logger.logger.info("Знайдено несумісні квіти");
                console.setText("Знайдено несумісні квіти");
            }
        } else {
            Logger.logger.info("Помилка введення типу квітки.");
            console.setText("Помилка введення типу квітки.");
        }
    }
    @FXML
    private void deleteBouquet(){
        int id;
        try{
            id = Integer.parseInt(bouquetIdText.getText());
        }catch (Exception e){
            console.setText("Помилка введення.");
            Logger.logger.warning("Помилка введення : "+e);
            return;
        }
        for(Bouquet bouquet: bouquets){
            if(bouquet.getId()==id){
                SQlConnection.deleteBouquet(connection,id);
                console.setText("Було видалено букет з id: "+id);
                Logger.logger.info("Було видалено букет з id: "+id);
                bouquetIdText.clear();
                return;
            }
        }
        console.setText("Такого букета неіснує.");
        Logger.logger.info("Такого букета неіснує.");
    }
    @FXML
    private void updateBouquet(){
        int id,reduce;
        try{
            id = Integer.parseInt(bouquetIdText.getText());
            reduce =Integer.parseInt(reduceLevel.getText());
        }catch (Exception e){
            console.setText("Помилка введення");
            Logger.logger.warning("Помилка введення"+e);
            return;
        }
        for(Bouquet bouquet: bouquets){
            if(bouquet.getId()==id){
                int newPrice =updateFlowersInBouquet(id,reduce,bouquet.getDecorPrice());
                if(newPrice==0){

                    return;
                }
                SQlConnection.updateBouquet(connection,id,newPrice);
                console.setText("Букет було успішно модифіковано.");
                Logger.logger.fine("Букет було успішно модифіковано.");
                bouquetIdText.clear();
                reduceLevel.clear();
                return;
            }
        }
        Logger.logger.info("Такого букета неіснує.");
        console.setText("Такого букета неіснує.");
    }
    @FXML
    private void createBouquetFromArray(){
        if(FlowersIdString.getText().isEmpty()){
            console.setText("Список id порожній.");
            Logger.logger.info("Список id порожній.");
            return;
        }
        int[] array;
        int decorPrice;
        try{
            array = Array.convertStringIntoArray(FlowersIdString.getText());
            array = Array.removeDuplicates(array);
            System.out.println(Arrays.toString(array));
            decorPrice = Integer.parseInt(decorPriceOfNewBouquet.getText());
        }
        catch (Exception e){
            console.setText("Неправильне введення.");
            Logger.logger.warning("Неправильне введення :"+e);
            return;
        }
        int id = searchEmptyID();
        if(typeOfFlower.getText().contains("SeasonFlower")) {
            ArrayList<SeasonFlower>seasonFlowers=SQlConnection.selectSeasonFlowersFromArray(connection,array);
            if(Compatibility.checkConditionsOfCreatingSeasonBouquet(seasonFlowers)) {
                int totalPrice = decorPrice;
                for(SeasonFlower flower:seasonFlowers){
                    totalPrice+=flower.getPrice();
                }
                SQlConnection.createSeasonFlowersBouquetFromArray(id,totalPrice,wrapperOfNewBouquet.getText(),
                        decorPrice, extraElementsOfNewBouquet.getText(), array, connection);
                console.setText("Букет було створено.");
                Logger.logger.fine("Сезонний букет було створено");
                clearInput();
            }
            else{
                Logger.logger.info("Знайдено несумісні квіти");
                console.setText("Знайдено несумісні квіти");
            }
        } else if (typeOfFlower.getText().contains("TropicalFlower")) {
            ArrayList<TropicalFlower>tropicalFlowers=
                    SQlConnection.selectTropicalFlowersFromArray(connection,array);
            if(Compatibility.checkConditionsOfCreatingTropicalBouquet(tropicalFlowers)){
                int totalPrice = decorPrice;
                for(TropicalFlower flower:tropicalFlowers){
                    totalPrice+=flower.getPrice();
                }
                SQlConnection.createTropicalFlowersBouquetFromArray(id,totalPrice,wrapperOfNewBouquet.getText(),
                        decorPrice, extraElementsOfNewBouquet.getText(),array, connection);
                console.setText("Букет було створено.");
                Logger.logger.fine("Тропічний букет було створено");
            }
            else{
                console.setText("Знайдено несумісні квіти");
                Logger.logger.info("Знайдено несумісні квіти.");
            }
        } else {
            console.setText("Помилка введення типу квітки.");
            Logger.logger.info("Помилка введення типу квітки.");
        }
    }
    private void initTropicalFlowersList(int id){
        flowers.clear();
        ArrayList<TropicalFlower> list = SQlConnection.selectTropicalFlowersFromBouquet(connection,id);
        for(TropicalFlower flower:list) {
            Flower Flower = new Flower(flower.getId(),flower.getName(),flower.getColor(),flower.getFreshnessLevel(),
                    flower.getStemLength(),flower.getPrice(),flower.getCountryOfOrigin(),
                    flower.isCompatibleWithOthers());
            flowers.add(Flower);
        }
        Logger.logger.info("Було згенеровано список тропічних квітів");
    }
    private void initSeasonFlowersList(int id){
        flowers.clear();
        ArrayList<SeasonFlower> list = SQlConnection.selectSeasonFlowersFromBouquet(connection,id);
        for(SeasonFlower flower:list) {
            Flower Flower = new Flower(flower.getId(),flower.getName(),flower.getColor(),flower.getFreshnessLevel(),
                    flower.getStemLength(),flower.getPrice(),flower.getCountryOfOrigin(),true);
            flowers.add(Flower);
        }
        Logger.logger.info("Було згенеровано список сезонних квітів");
    }
   private void initBouquetsList(){
       String query = "select * from Bouquets";
       try{
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           while(resultSet.next()) {
               Bouquet bouquet;
               bouquet = new Bouquet(resultSet.getInt("bouquet_ID"),resultSet.getInt("price"),
                       resultSet.getString("wrapperType"), resultSet.getString("extraElements"),
                       resultSet.getInt("decorPrice"));
               bouquets.add(bouquet);
           }
       }
       catch(SQLException e) {
           Logger.logger.severe("Помилка під час виконання запиту : "+e);
           Email.SendMessage("Помилка під час виконання запиту: ",e);
       }
   }
    private int searchEmptyID(){
        ArrayList<Integer> bouquetId = new ArrayList<>();
        for (Bouquet bouquet : bouquets) {
            bouquetId.add(bouquet.getId());
        }
        int id=1;
        while(bouquetId.contains(id)){
            id++;
        }
        bouquetId.add(id);
        return id;
    }
   private int updateFlowersInBouquet(int id,int reduce,int decorPrice){
       ArrayList<SeasonFlower> seasonFlowers = SQlConnection.selectSeasonFlowersFromBouquet(connection,id);
       ArrayList<TropicalFlower> tropicalFlowers = SQlConnection.selectTropicalFlowersFromBouquet(connection,id);
       int newPrice =decorPrice;
       if(tropicalFlowers.isEmpty()) {
           for (SeasonFlower flower : seasonFlowers) {
               flower.reduceFreshnessLevel(reduce);
               if(flower.getFreshnessLevel()==0){
                   SQlConnection.deleteBouquet(connection,id);
                   console.setText("Букет було видалено оскільки він зів'яв");
                   Logger.logger.info("Букет було видалено оскільки він зів'яв");
                   bouquetIdText.clear();
                   reduceLevel.clear();
                   return 0;
               }
               SQlConnection.updateSeasonFlower(flower.getId(), flower.getFreshnessLevel(), flower.getPrice(), connection);
               newPrice+= flower.getPrice();
               Logger.logger.info("Було оновлено квіти в базі");
           }
       }
       else {
           for (TropicalFlower flower : tropicalFlowers) {
               flower.reduceFreshnessLevel(reduce);
               if(flower.getFreshnessLevel()==0){
                   SQlConnection.deleteBouquet(connection,id);
                   console.setText("Букет було видалено оскільки він зів'яв");
                   Logger.logger.info("Букет було видалено оскільки він зів'яв");
                   bouquetIdText.clear();
                   reduceLevel.clear();
                   return 0;
               }
               SQlConnection.updateTropicalFlower(connection,flower.getId(),flower.getPrice(), flower.getFreshnessLevel());
               newPrice+= flower.getPrice();
               Logger.logger.info("Було оновлено квіти в базі");
           }
       }
       return newPrice;
   }
   @FXML
    private void saveBouquetInFile() {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:\\LP\\Bouquet.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fife){
            Logger.logger.severe("Не вдалося відкрити файл для запису :"+fife);
            Email.SendMessage("Не вдалося відкрити файл для запису :",fife);
            System.err.println("Не вдалося відкрити файл для запису.");
            return ;
        }
        printBouquetFlowersWithId();
        int id;
        try {
            id = Integer.parseInt(searchId.getText());
        }
        catch (Exception e){
            console.setText("Помилка введення id.");
            Logger.logger.warning("Помилка введення id."+e);
            return;
        }
        for(Bouquet bouquet:bouquets){
            if(bouquet.getId()==id){
                System.out.println(bouquet);
            }
        }
        for(Flower flower:flowers) {
            System.out.println(flower.toString());
        }
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(console);
        Logger.logger.info("Було збережено букет і його квіти у файл.");
    }

}
