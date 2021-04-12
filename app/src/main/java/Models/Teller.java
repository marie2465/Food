package Models;

import android.os.Parcelable;

import java.util.zip.ZipEntry;

/**
 * Created by 208-it-01 on 12.04.2018.
 */

public class Teller
{
    private String name;
    private String desc;
    private String url;
    private float price;
    private String  Pizza;
    private String Sushi;
    private String Zakuski;
    private String Salat;
    private String Soup;
    private String Meat;
    private String Fish;
    private String Sous;
    private String Desert;
    private String Drinks;

    public Teller(String name, String desc, String url, String price, String pizza, String sushi, String zakuski, String salat, String soup, String meat, String fish, String sous, String desert, String drinks) {
        this.name = name;
        this.desc = desc;
        this.url = url;
        this.price = Float.parseFloat(price);
        Pizza = pizza;
        Sushi = sushi;
        Zakuski = zakuski;
        Salat = salat;
        Soup = soup;
        Meat = meat;
        Fish = fish;
        Sous = sous;
        Desert = desert;
        Drinks = drinks;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public float getPrice() {
        return price;
    }

    public String getPizza() {
        return Pizza;
    }

    public String getSushi() {
        return Sushi;
    }

    public String getZakuski() {
        return Zakuski;
    }

    public String getSalat() {
        return Salat;
    }

    public String getSoup() {
        return Soup;
    }

    public String getMeat() {
        return Meat;
    }

    public String getFish() {
        return Fish;
    }

    public String getSous() {
        return Sous;
    }

    public String getDesert() {
        return Desert;
    }

    public String getDrinks() {
        return Drinks;
    }
}
