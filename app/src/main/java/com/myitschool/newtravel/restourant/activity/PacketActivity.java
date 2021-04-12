package com.myitschool.newtravel.restourant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.myitschool.newtravel.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import CircleMenu.CircleMenuView;
import Util.Constants;
import flipviewpager.adapters.RestourantsAdapter;

public class PacketActivity extends AppCompatActivity {

    private int id;
    private String phone;
    private int count;
    private String comment;
    private String time_start;
    private String time_stop;
    private String info;
    private ImageView image;
    private TextView cafe_name;
    private TextView commentary;
    private ExpandableTextView info1;
    private TextView count1;
    private String title;
    private CircleMenuView circleMenuView;
    private LinearLayout packets;
    private String url;
    private TextView TextPhone;
    private LinearLayout call;
    private int Busness;
    private int TableOnline;
    private int Vitrina;
    private int MenuOnline;
    private int Bancket;
    private int DayAnshlag;
    private int MoreClients;

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet);
        Typeface code = Typeface.createFromAsset(getAssets(), "fonts/whitney_book.ttf");
        image = (ImageView) findViewById(R.id.image);
        cafe_name = (TextView) findViewById(R.id.cafe_name);
        cafe_name.setTypeface(code);
        commentary = (TextView) findViewById(R.id.commentary);
        commentary.setTypeface(code);
        info1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
        count1 = (TextView) findViewById(R.id.count);
        count1.setTypeface(code);
        circleMenuView=(CircleMenuView)findViewById(R.id.circle_menu);
        packets=(LinearLayout)findViewById(R.id.packets);
        TextPhone=(TextView)findViewById(R.id.info);
        TextPhone.setTypeface(code);
        call=(LinearLayout)findViewById(R.id.call);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            Busness = extras.getInt("Busness");
            TableOnline=extras.getInt("TableOnline");
            Vitrina=extras.getInt("Vitrina");
            MenuOnline=extras.getInt("MenuOnline");
            Bancket=extras.getInt("Bancket");
            DayAnshlag=extras.getInt("DayAnshlag");
            MoreClients=extras.getInt("MoreClients");
            phone = extras.getString("phone");
            comment = extras.getString("comment");
            count = extras.getInt("count");
            time_start = extras.getString("time_start");
            time_stop = extras.getString("time_stop");
            info = extras.getString("info");
            title=extras.getString("title");
            url=extras.getString("url");
        }
        cafe_name.setText(title);
        commentary.setText("Время работы с "+time_start+" до "+time_stop);
        info1.setText(info);
        count1.setText("Количество мест:"+Integer.toString(count));
        TextPhone.setText(phone);
        if(TableOnline==0&&Vitrina==0&&MenuOnline==0&&Bancket==0&&DayAnshlag==0&&MoreClients==0)
        {
            packets.setVisibility(View.GONE);
        }
        else  packets.setVisibility(View.VISIBLE);
        if(TableOnline==1) circleMenuView.Packet1();
        if(Vitrina==1) circleMenuView.Packet2();
        if(MenuOnline==1) circleMenuView.Packet3();
        if(Bancket==1) circleMenuView.Packet4();
        if(DayAnshlag==1) circleMenuView.Packet5();
        if(MoreClients==1) circleMenuView.Packet6();
        circleMenuView.setParemetrs(id);
        Picasso.with(this).load(Constants.apilink+url).into(image);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:"+phone.trim());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
    }
}
