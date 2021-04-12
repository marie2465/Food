package com.myitschool.newtravel.restourant.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myitschool.newtravel.R;

import java.util.ArrayList;

import Models.Restourant;
import Models.Restourants;
import flipviewpager.adapters.PagerAdapter;

public class RestourantsActivity extends AppCompatActivity {
    public static final String EXTRA_REST="rest";
    public static final String EXTRA_NUM="num";
    private ArrayList<Restourant> list;
    private int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restourants);
        list=new ArrayList<Restourant>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            list = extras.getParcelableArrayList(EXTRA_REST);
            num=extras.getInt(EXTRA_NUM,0);
        }

        ViewPager pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(),list));
        pager.setCurrentItem(num);
    }
}
