package models;

public class Flower {
    private final int id;
    private final String name;
    private final String color;
    private int freshnessLevel;
    private final int stemLength;
    private int price;
    private final String countryOfOrigin;
    private final boolean compatibility;

    public Flower(int id, String name, String color, int freshnessLevel, int stemLength, int price,
                  String countryOfOrigin, boolean compatibility) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.freshnessLevel = freshnessLevel;
        this.stemLength = stemLength;
        this.price = price;
        this.countryOfOrigin = countryOfOrigin;
        this.compatibility = compatibility;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public boolean isCompatibility() {
        return compatibility;
    }

    public int getId() {
        return id;
    }

    public int getFreshnessLevel() {
        return freshnessLevel;
    }

    public int getStemLength() {
        return stemLength;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
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
                "Країна походження: " + countryOfOrigin + '\n'+
                "Сумісність з іншими: "+compatibility+'\n';

    }
}
