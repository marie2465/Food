package Models;

import java.util.ArrayList;

/**
 * Created by 208-it-01 on 28.03.2018.
 */

public class Restourants
{
    private ArrayList<Restourant> restourants;

    public Restourants(ArrayList<Restourant> restourants) {
        this.restourants = restourants;
    }
    public ArrayList<Restourant> getRestourants() {
        return restourants;
    }

    public void setMessages(ArrayList<Restourant> messages) {
        this.restourants = messages;
    }
}
