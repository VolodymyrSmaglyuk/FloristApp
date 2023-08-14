package models;

public class TropicalFlower {
    private int id;
    private final String name;
    private final String color;
    private int freshnessLevel;
    private final int stemLength;
    private final boolean compatibleWithOthers;
    private final String countryOfOrigin;
    private int price;
    private int bouquetID;

    public TropicalFlower(int id, String name, String color, int freshnessLevel, int stemLength,
                          boolean compatibleWithOthers, String countryOfOrigin, int price,int bouquetID) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.freshnessLevel = freshnessLevel;
        this.stemLength = stemLength;
        this.compatibleWithOthers = compatibleWithOthers;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.bouquetID=bouquetID;
    }
    public void reduceFreshnessLevel(int reduceNumb){
        int tmp = this.freshnessLevel-reduceNumb;
        this.price =(int) (this.price - (this.price * ((this.freshnessLevel - tmp) / 10.0)));
        this.freshnessLevel=tmp;
        if(freshnessLevel<0){
            this.freshnessLevel=0;
            this.price=0;
        }
    }
    public String toString() {
        return
                "Назва квітки: " + name +'\n' +
                        "Колір: " + color + '\n'+
                        "Рівень свіжості: " + freshnessLevel +'\n'+
                        "Довжина стебла: " + stemLength +'\n'+
                        "Ціна за одиницю: " + price + '\n'+
                        "Сумісність з іншими квітками: " + compatibleWithOthers + '\n' +
                        "Країна походження: " + countryOfOrigin + '\n';
    }

    public boolean isCompatibleWithOthers() {
        return compatibleWithOthers;
    }
    public int getId() {
        return this.id;
    }

    public int getFreshnessLevel() {
        return this.freshnessLevel;
    }

    public int getStemLength() {
        return this.stemLength;
    }

    public int getPrice() {
        return this.price;
    }

    public int getBouquetID() {
        return this.bouquetID;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }


    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
}
