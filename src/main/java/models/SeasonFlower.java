package models;


public class SeasonFlower {
    private int id;
    private final String name;
    private final String color;
    private int freshnessLevel;
    private final int stemLength;
    private final String flowerSeason;
    private final String countryOfOrigin;
    private int price;
    private int bouquetID;

    public SeasonFlower(int id, int freshnessLevel, int stemLength, int price,
                          int bouquetID, String name, String color, String flowerSeason, String countryOfOrigin) {
        this.id=id;
        this.freshnessLevel=freshnessLevel;
        this.stemLength=stemLength;
        this.price=price;
        this.bouquetID=bouquetID;
        this.name=name;
        this.color=color;
        this.flowerSeason = flowerSeason;
        this.countryOfOrigin = countryOfOrigin;
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
                        "Сезон квітки: " + flowerSeason + '\n' +
                        "Країна походження: " + countryOfOrigin + '\n';
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
    public String getFlowerSeason() {
        return flowerSeason;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
}
