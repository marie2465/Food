package com.myitschool.newtravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import Util.Constants;

;
import java.util.ArrayList;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


public class CityFragment extends Fragment {

    @BindView(R.id.cityname) AutoCompleteTextView cityname;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.music_list) ListView lv;

    List<String> id     = new ArrayList<>();
    List<String> list2  = new ArrayList<>();

    private String nameyet;
    private String cityid;
    private Activity activity;
    private Typeface tex;
    private Handler mHandler;

    public CityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.content_citylist, container, false);

        ButterKnife.bind(this,v);

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);

        mHandler = new Handler(Looper.getMainLooper());
        tex = Typeface.createFromAsset(activity.getAssets(), "fonts/texgyreadventor-bold.otf");
        cityname.setThreshold(1);



        return v;
    }

}
