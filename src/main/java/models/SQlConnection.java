package models;


import java.sql.*;
import java.util.ArrayList;


public class SQlConnection {
    public static Connection ConnectDB() throws SQLException, ClassNotFoundException {
        Connection conn;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String Url = "jdbc:sqlserver://LENOVO;databaseName=FloristDB;encrypt=true;trustServerCertificate=true;";
        conn = DriverManager.getConnection(Url, "Test", "12345");
        return conn;
    }
    public static void insertSeasonFlower( int id,int freshnessLevel, int stemLength, int price, String name,
                                           String color, String flowerSeason, String countryOfOrigin,Connection conn) {
        try {
            Statement st = conn.createStatement();
            String query = "insert into SeasonFlowers (flower_ID,name,color,FreshnessLevel," +
                    "stemLength,price,flowerSeason,countryOfOrigin) " + "values(" + id + ", " + addQuotation(name) + "," +
                    addQuotation(color) + ", " + freshnessLevel + ", " + stemLength + ", " + price + ", " +
                    addQuotation(flowerSeason) + ", " + addQuotation(countryOfOrigin) + " )";
            st.execute(query);
        }
        catch ( SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void InsertTropicalFlower(int id,int freshnessLevel, int stemLength, int price, String name,
                                            String color, int compatibility, String countryOfOrigin,Connection conn){
        try {
            Statement st = conn.createStatement();
            String query = "insert into TropicalFlowers " +
                    "(flower_ID,name,color,freshnessLevel,stemLength,price,compatibleWithOthers," +
                    "countryOfOrigin) values(" + id + ", " + addQuotation(name) + ", " + addQuotation(color) + ", " +
                    freshnessLevel + ", " + stemLength + "," + price + ", " + compatibility + "," +
                    addQuotation(countryOfOrigin) + ")";
            st.execute(query);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
    public static void deleteTropicalFlower(Connection conn,int id){
        try{
            Statement st = conn.createStatement();
            String query = "delete from TropicalFlowers where flower_ID = "+id;
            st.execute(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void updateTropicalFlower(Connection conn,int id,int price,int freshness){
        try{
            Statement st = conn.createStatement();
            String query = "update TropicalFLowers set freshnessLevel = "+freshness+", price = "+price+
                    " where flower_ID = "+id;
            st.execute(query);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void updateSeasonFlower(int id,int freshnessLevel,int price,Connection connection){
        try {
            Statement st = connection.createStatement();
            String query = "update SeasonFlowers set FreshnessLevel = " + freshnessLevel + ", " + " price = " + price +
                    "where flower_ID = " + id;
            st.execute(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void deleteSeasonFlower(Connection connection,int id){
        try {
            Statement st = connection.createStatement();
            st.execute("delete from SeasonFlowers where flower_ID = " + id);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void createSeasonFlowersBouquetFromInterval(int price,int id,String wrapper,int decorPrice,
                                                 String extraElements,int start,int end,Connection conn) {
        try {
            Statement st = conn.createStatement();
            String query = "update SeasonFlowers set bouquetID = " + id + "where flower_ID between " + start + " and " + end;
            st.execute(query);
            String query2 = "insert into Bouquets (bouquet_ID,price,wrapperType,extraElements,decorPrice) values ( " +
                    id + ", " + price + ", " + addQuotation(wrapper) + ", " + addQuotation(extraElements) + ", " + decorPrice + " )";
            st.execute(query2);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void createTropicalFlowersBouquetFromInterval(int price,int id,String wrapper,int decorPrice,
                                                              String extraElements,int start,int end,Connection conn) {
        try {
            Statement st = conn.createStatement();
            String query = "update SeasonFlowers set bouquetID = " + id + "where flower_ID between " + start + " and " + end;
            st.execute(query);
            String query2 = "insert into Bouquets (bouquet_ID,price,wrapperType,extraElements,decorPrice) values ( " +
                    id + ", " + price + ", " + addQuotation(wrapper) + ", " + addQuotation(extraElements) + ", " + decorPrice + " )";
            st.execute(query2);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void createTropicalFlowersBouquetFromArray(int id,int price,String wrapper,int decorPrice,
                                                             String extraElements,int[] arr,Connection conn) {
        try{
            Statement st = conn.createStatement();
            for (int j : arr) {
                String query = "update TropicalFlowers set bouquetID = " + id + " where flower_ID = " + j;
                st.execute(query);
            }
            String query = "insert into Bouquets (bouquet_ID,price,wrapperType,extraElements,decorPrice) values ( " +
                    id + ", " + price + ", " + addQuotation(wrapper) + ", " + addQuotation(extraElements) + ", " + decorPrice + " )";
            st.execute(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void createSeasonFlowersBouquetFromArray(int id,int price,String wrapper,int decorPrice,
                                                           String extraElements,int[] arr,Connection conn){
        try{
            Statement st = conn.createStatement();
            for (int j : arr) {
                String query = "update SeasonFlowers set bouquetID = " + id + " where flower_ID = " + j;
                st.execute(query);
            }
            String query = "insert into Bouquets (bouquet_ID,price,wrapperType,extraElements,decorPrice) values ( " +
                    id + ", " + price + ", " + addQuotation(wrapper) + ", " + addQuotation(extraElements) + ", " + decorPrice + " )";
            st.execute(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void deleteBouquet(Connection conn,int id){
        try {
            Statement st = conn.createStatement();
            st.execute("delete from Bouquets where bouquet_ID = " + id);
            st.execute("delete from SeasonFlowers where bouquetID = " + id);
            st.execute("delete from TropicalFlowers where bouquetID = " + id);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void  updateBouquet(Connection conn,int id,int newPrice){
        try {
            Statement st = conn.createStatement();
            String query = "update Bouquets set price = " + newPrice + " where bouquet_ID = " + id;
            st.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static String addQuotation(String text){
        text = "'"+text+"'";
        return text;
    }
    public static ArrayList<SeasonFlower> selectSeasonFlowersOnInterval(Connection conn,int start,int end){
       ArrayList<SeasonFlower> list=new ArrayList<>();
        try {
           Statement st = conn.createStatement();
           String query = "select * from SeasonFlowers where flower_ID between "+ start +" and "+end;
            ResultSet resultSet = st.executeQuery(query);
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
       catch (SQLException e){
           throw new RuntimeException(e);
       }
        return list;
    }
    public static ArrayList<TropicalFlower> selectTropicalFlowersOnInterval(Connection conn,int start,int end){
        ArrayList<TropicalFlower> list=new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            String query = "select * from TropicalFlowers where flower_ID between "+ start +" and "+end;
            ResultSet resultSet = st.executeQuery(query);
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
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    public static ArrayList<SeasonFlower> selectSeasonFlowersFromArray(Connection conn,int[] arr){
        ArrayList<SeasonFlower> list=new ArrayList<>();
        try{
            Statement st = conn.createStatement();
            for (int j : arr) {
                String query = "select * from SeasonFlowers where flower_ID = " + j;
                ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
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
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return  list;
    }
    public static ArrayList<TropicalFlower> selectTropicalFlowersFromArray(Connection conn,int[] arr){
        ArrayList<TropicalFlower> list=new ArrayList<>();
        try{
            Statement st = conn.createStatement();
            for (int j : arr) {
                String query = "select * from TropicalFlowers where flower_ID = " + j;
                ResultSet resultSet = st.executeQuery(query);
                while (resultSet.next()) {
                    TropicalFlower flower;
                    flower = new TropicalFlower(resultSet.getInt("flower_ID"),
                            resultSet.getString("name"), resultSet.getString("color"),
                            resultSet.getInt("FreshnessLevel"), resultSet.getInt("stemLength"),
                            resultSet.getBoolean("compatibleWithOthers"),
                            resultSet.getString("countryOfOrigin"), resultSet.getInt("price"),
                            resultSet.getInt("bouquetID"));
                    list.add(flower);
                }
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return  list;
    }

    public static ArrayList<SeasonFlower> selectSeasonFlowersFromBouquet(Connection connection, int id){
        ArrayList<SeasonFlower> list = new ArrayList<>();
        String query;
        if(id ==0){
            query = "select * from SeasonFlowers where bouquetID is null";
        }
        else {
            query = "select * from SeasonFlowers where bouquetID = " + id;
        }
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
            throw new RuntimeException(e);
        }
        return list;
    }
    public  static ArrayList<TropicalFlower> selectTropicalFlowersFromBouquet(Connection connection, int id){
        ArrayList<TropicalFlower> list = new ArrayList<>();
        String query;
        if(id ==0){
            query = "select * from TropicalFlowers where bouquetID is null";
        }
        else {
            query = "select * from TropicalFlowers where bouquetID = "+id;
        }
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
            throw new RuntimeException(e);
        }
        return list;
    }

}
