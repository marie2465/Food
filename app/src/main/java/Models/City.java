package Models;

/**
 * Created by swati on 25/1/16.
 */

import android.widget.ImageView;

/**
 * Model class for city object
 */
public class City {
    private final String name;
    private final String id;
    private final float lat;
    private final float lng;
    private final ImageView background;


    /**
     * Instantiates city object
     *
     * @param id         unique id for a city
     * @param name   city name
     * @param background Background color when card is opened
     * @param lat        latitude of city
     * @param lng        longitude of city
     */
    public City(String id, String name, ImageView background, float lat, float lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.background = background;
    }

    public String getNickname() {
        return name;
    }

    public String getId() {
        return id;
    }

    public float getLa() {
        return lat;
    }

    public float getLo() {
        return lng;
    }

    public ImageView getBackground() {
        return background;
    }

}