package com.myitschool.newtravel.restourant.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.myitschool.newtravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Models.Otziv;
import Util.Constants;
import flipviewpager.adapters.VitrinaAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VitrinaActivity extends AppCompatActivity {
    private ArrayList<Otziv> vitr;
    private String message;
    private int id_user;
    private int id;
    private FloatingActionButton otzev_ok;
    private EditText messag;

    private SharedPreferences sharedPreferences;
    private RecyclerView rc;
    private VitrinaAdapter adapter;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitrina);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id_user=Integer.parseInt(sharedPreferences.getString(Constants.USER_ID,""));
        vitr = new ArrayList<Otziv>();
        otzev_ok=(FloatingActionButton)findViewById(R.id.otzyv_ok);
        messag=(EditText)findViewById(R.id.id_messsage);
        rc=(RecyclerView)findViewById(R.id.vitrinaList);
        mHandler = new Handler(Looper.getMainLooper());
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            id = extras.getInt("id");
        }
        Virt();
        otzev_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vit();
                messag.setText("");
            }
        });
    }
    public void Vit()
    {
        message=messag.getText().toString();
        String uri = Constants.apilink + "vitrina.php?message=" +message+"&id_user="+id_user+"&rest_id="+id;
        //Set up client
        OkHttpClient client = new OkHttpClient();
        //Execute request
        Request request = new Request.Builder()
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res=response.body().string();
                vitr.clear();
                Virt();
            }
        });

    }

     public void Virt()
    {
        String uri = Constants.apilink + "ivitrina.php?rest_id="+id;

        //Set up client
        OkHttpClient client = new OkHttpClient();
        //Execute request
        Request request = new Request.Builder()
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res=response.body().string();
                try {
                    JSONArray obj=new JSONArray(res);
                    for(int i=0;i<obj.length();i++)
                    {
                        JSONObject o=obj.getJSONObject(i);
                        String mes=o.getString("message");
                        int id_user=Integer.parseInt(o.getString("id_user"));
                        String name=o.getString("name");
                        Otziv otz=new Otziv(mes,id_user,name);
                        vitr.add(otz);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter=new VitrinaAdapter(vitr,getApplicationContext());
                        rc.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        });

    }

}