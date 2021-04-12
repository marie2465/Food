package Models;

import java.util.ArrayList;

/**
 * Created by 208-it-01 on 12.04.2018.
 */

public class Tellers
{
    private ArrayList<Teller> tellers;

    public Tellers(ArrayList<Teller> tell) {
        this.tellers = tell;
    }

    public ArrayList<Teller> getTellers() {
        return tellers;
    }

    public void setTellers(ArrayList<Teller> tellers) {
        this.tellers = tellers;
    }
}
