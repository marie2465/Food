package Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by 208-it-01 on 28.03.2018.
 */

public class Restourant implements Parcelable
{
    private  int rest_id;
    private  String city_name;
    private  String title;
    private String image_url_main;
    private  String comment;
    private  int count;
    private float lat;
    private float lng;
    private  String  time_start;
    private  String time_stop;
    private int Busness;
    private String telephone;
    private String info;
    private int TableOnline;
    private int Vitrina;
    private int MenuOnline;
    private int Bancket;
    private int DayAnshlag;
    private int MoreClients;


    public Restourant(int rest_id, String city_name, String title, String image_url_main, String comment, int count, String time_start, String time_stop, String lat, String  lng,String Busness,String telephone,String info,String TableOnline,String Vitrina,String MenuOnline,String Bancket,String DayAnshlag,String MoreClients) {
        this.rest_id = rest_id;
        this.city_name = city_name;
        this.title = title;
        this.image_url_main = image_url_main;
        this.comment = comment;
        this.count = count;
        this.time_start = time_start;
        this.time_stop = time_stop;
        this.lat=Float.parseFloat(lat);
        this.lng=Float.parseFloat(lng);
        this.Busness=Integer.parseInt(Busness);
        this.TableOnline=Integer.parseInt(TableOnline);
        this.Vitrina=Integer.parseInt(Vitrina);
        this.MenuOnline=Integer.parseInt(MenuOnline);
        this.Bancket=Integer.parseInt(Bancket);
        this.DayAnshlag=Integer.parseInt(DayAnshlag);
        this.MoreClients=Integer.parseInt(MoreClients);
        this.telephone=telephone;
        this.info=info;
    }

    protected Restourant(Parcel in) {
        rest_id = in.readInt();
        city_name = in.readString();
        title = in.readString();
        image_url_main = in.readString();
        comment = in.readString();
        count = in.readInt();
        time_start = in.readString();
        time_stop = in.readString();
        lat=in.readFloat();
        lng=in.readFloat();
        Busness=in.readInt();
        TableOnline=in.readInt();
        Vitrina=in.readInt();
        MenuOnline=in.readInt();
        Bancket=in.readInt();
        DayAnshlag=in.readInt();
        MoreClients=in.readInt();
        telephone=in.readString();
        info=in.readString();
    }

    public static final Creator<Restourant> CREATOR = new Creator<Restourant>() {
        @Override
        public Restourant createFromParcel(Parcel in) {
            return new Restourant(in);
        }

        @Override
        public Restourant[] newArray(int size) {
            return new Restourant[size];
        }
    };

    public int getRest_id() {
        return rest_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url_main() {
        return image_url_main;
    }

    public String getComment() {
        return comment;
    }

    public int getCount() {
        return count;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_stop() {
        return time_stop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public int getBusness() {
        return Busness;
    }

    public int getTableOnline() {
        return TableOnline;
    }

    public int getVitrina() {
        return Vitrina;
    }

    public int getMenuOnline() {
        return MenuOnline;
    }

    public int getBancket() {
        return Bancket;
    }

    public int getDayAnshlag() {
        return DayAnshlag;
    }

    public int getMoreClients() {
        return MoreClients;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rest_id);
        parcel.writeString(city_name);
        parcel.writeString(title);
        parcel.writeString(image_url_main);
        parcel.writeString(comment);
        parcel.writeInt(count);
        parcel.writeString(time_start);
        parcel.writeString(time_stop);
        parcel.writeFloat(lat);
        parcel.writeFloat(lng);
        parcel.writeInt(Busness);
        parcel.writeInt(TableOnline);
        parcel.writeInt(Vitrina);
        parcel.writeInt(MenuOnline);
        parcel.writeInt(Bancket);
        parcel.writeInt(DayAnshlag);
        parcel.writeInt(MoreClients);
        parcel.writeString(telephone);
        parcel.writeString(info );
    }
}
