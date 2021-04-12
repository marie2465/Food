package com.myitschool.newtravel.restourant.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.myitschool.newtravel.R;
import com.myitschool.newtravel.restourant.fragments.CustomFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Util.Constants;
import Util.Datable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TableOnlineActivity extends AppCompatActivity implements Datable {
    private int id;
    private Handler mHandler;
    private MaterialDialog dialog;
    private LinearLayout linear;
    private  int[][] matrix;
    HorizontalScrollView root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            GetMatrix(id);
        }
        mHandler = new Handler(Looper.getMainLooper());
        Online();
    }
    private void GetMatrix(int id)
    {
        dialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content("Fetching trips...")
                .progress(true, 0)
                .show();
        String uri = Constants.apilink + "matrix.php?id=" + id;
        OkHttpClient client = new OkHttpClient();
        //Execute request
        final Request request = new Request.Builder()
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("Request Failed", "Message : " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {

                final String res = response.body().string();
                ArrayList<String> list = new ArrayList<String>();
                final JSONArray arr;
                try {
                    arr = new JSONArray(res);
                    for (int i=0;i<arr.length();i++)
                    {
                        list.add( arr.getString(i) );
                    }
                    matrix=new int[list.size()][];
                    for(int i=0;i<list.size();i++)
                    {
                        String str=list.get(i).substring(2,list.get(i).length()-2);
                        String[] mas=str.split("\",\"");
                        matrix[i]=new int[mas.length];
                    }
                    for(int i=0;i<list.size();i++)
                    {
                        String str=list.get(i).substring(2,list.get(i).length()-2);
                        String[] mas=str.split("\",\"");
                        for(int j=0;j<mas.length;j++)
                        {
                            int k=Integer.parseInt(mas[j]);
                            matrix[i][j]=k;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("erro", e.getMessage() + " ");
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Fun();
                    }
                });

            }
        });
    }

    private void Fun()
    {
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        root=new HorizontalScrollView(getApplicationContext());
        //root.setBackgroundColor(Color.rgb(126,240,237));
        root.setLayoutParams(rootParams);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear = new LinearLayout(getApplicationContext());
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(linearParams);
        root.addView(linear);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
                125,  125);

        for(int i=0;i<matrix.length;i++) {
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linear.addView(linearLayout);

            for(int j=0;j<matrix[i].length;j++)
            {

                int m=matrix[i][j];
                if(m==-1)
                {
                TextView Empty = new TextView(getApplicationContext());
                Empty.setLayoutParams(par);
               // Empty.setBackgroundColor(Color.rgb(126,240,237));
                Empty.setTag("Empty");
                linearLayout.addView(Empty);
            }
            else if(m==0)
            {
                ImageView chair = new ImageView(getApplicationContext());
                chair.setLayoutParams(par);
                chair.setImageResource(R.drawable.chair);
                //chair.setBackgroundColor(Color.rgb(126,240,237));
                linearLayout.addView(chair);
            }
            else
            {
                TextView table = new TextView(getApplicationContext());
                table.setLayoutParams(par);
                table.setText(Integer.toString(matrix[i][j]));
                final int k=i;
                final int l=j;
                table.setTextSize(25);
                table.setTextColor(getResources().getColor(R.color.black));
                table.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_HORIZONTAL);
                table.setBackgroundColor(Color.GREEN);
                table.setTag(Integer.toString(matrix[i][j]));
                table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomFragment dialog = new CustomFragment();
                        Bundle args = new Bundle();
                        args.putInt("id_rest", id);
                        args.putInt("table_number",matrix[k][l]);
                        dialog.setArguments(args);
                        dialog.show(getSupportFragmentManager(), "custom");
                    }
                });
                linearLayout.addView(table);
            }
                setContentView(root);
            }
        }
    }
    private void Online()
    {
        Calendar t=Calendar.getInstance();
        Date d=t.getTime();
        int year=1900+d.getYear();
        int m=d.getMonth()+1;
        int day=d.getDate();
        String date_table=year+"-"+m+"-"+day;
        String uri = Constants.apilink + "busy.php?id=" +id+"&time="+date_table;
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
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                mHandler.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                          JSONObject ob = new JSONObject(res);
                                          JSONArray array=ob.getJSONArray("busy");
                                          ArrayList<Integer> listdata = new ArrayList<Integer>();
                                          for (int i=0;i<array.length();i++){
                                              listdata.add(Integer.parseInt(array.getString(i)));
                                          }
                                          if(listdata.size()!=0)
                                          {
                                              for (int m = 0; m < listdata.size(); m++) {
                                                  try {
                                                      int childCount = linear.getChildCount();
                                                      for (int i = 0; i < childCount; i++) {
                                                          LinearLayout l = (LinearLayout) linear.getChildAt(i);
                                                          int k = l.getChildCount();
                                                          for (int j = 0; j < k; j++) {
                                                              View child = l.getChildAt(j);
                                                              if (child instanceof TextView) {
                                                                  if (child.getTag().equals(Integer.toString(listdata.get(m)))) {
                                                                      child.setBackgroundColor(Color.RED);
                                                                  }
                                                              }
                                                          }
                                                      }
                                                  }
                                                  catch (NullPointerException ex)
                                                  {

                                                  }
                                              }
                                          }

                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                      mHandler.post(new Runnable() {
                                          @Override
                                          public void run()
                                          {

                                          }
                                      });
                                  }
                              }
                );
            }
        });
    }
    @Override
    public void sendMessage(final int table_number, int id_rest, int id_user, String time_with, String time_after, String data)
    {
        Toast.makeText(this,"Ваш заказ принят",Toast.LENGTH_LONG).show();
        String uri = Constants.apilink + "getuser.php?id=" + id_user;
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
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                mHandler.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                              JSONObject ob = new JSONObject(res);
                                              JSONArray o=ob.getJSONArray("user");
                                              JSONObject obj=o.getJSONObject(0);
                                              String name = obj.getString("name");
                                              String phone = obj.getString("phone");
                                              String mail=obj.getString("mail");


                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                      mHandler.post(new Runnable() {
                                          @Override
                                          public void run()
                                          {
                                              int childCount = linear.getChildCount();
                                              for (int i = 0; i < childCount; i++)
                                              {
                                                  LinearLayout l = (LinearLayout) linear.getChildAt(i);
                                                  int k = l.getChildCount();
                                                  for (int j = 0; j < k; j++)
                                                  {
                                                      View child =l.getChildAt(j);
                                                      if (child instanceof TextView)
                                                      {
                                                          if (child.getTag().equals(Integer.toString(table_number))) {
                                                              child.setBackgroundColor(Color.RED);
                                                          }
                                                      }
                                                  }
                                              }
                                          }
                                      });
                                  }
                              }
                );
            }
        });

    }
}