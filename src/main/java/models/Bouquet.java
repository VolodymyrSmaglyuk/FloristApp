package models;

public class Bouquet {
    private int id;
    private int bouquetPrice;
    private final String wrapperType;
    private final String extraElements;
    private final int decorPrice;

    public Bouquet(int id, int bouquetPrice, String wrapperType, String extraElements, int decorPrice) {
        this.id = id;
        this.bouquetPrice = bouquetPrice;
        this.wrapperType = wrapperType;
        this.extraElements = extraElements;
        this.decorPrice = decorPrice;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return bouquetPrice;
    }

    public String getWrapperType() {
        return wrapperType;
    }

    public String getExtraElements() {
        return extraElements;
    }

    public int getDecorPrice() {
        return decorPrice;
    }

    public String toString() {
        return "Інформація про букет:" +'\n'+
                "Вартість: " + bouquetPrice +'\n'+
                "Тип обгортки: " + wrapperType + '\n' +
                "Додаткові елементи: " + extraElements + '\n' +
                "Вартість обгортки і додаткових елементів: " + decorPrice+'\n';
    }
}
