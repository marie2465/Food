package com.myitschool.newtravel.restourant.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.myitschool.newtravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Models.TimeBisyTable;
import Util.Constants;
import Util.Datable;
import flipviewpager.adapters.BusyAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CustomFragment extends DialogFragment
{
    private Button timer;
    private Button timer1;
    private Button data;
    Calendar DateAndTime=Calendar.getInstance();
    private SharedPreferences sharedPreferences;
    private String id;
    private int table_number;
    private int id_rest;
    private Handler handler;
    private String time_with;
    private String time_after;
    private String date_table;
    private long tw;
    private long tt;
    private boolean b;
    private Datable datable;
    private RecyclerView busy;
    private BusyAdapter adapter;
    private Handler mHandler;
    ArrayList<TimeBisyTable> bisyTables;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        id_rest = getArguments().getInt("id_rest");
        table_number=getArguments().getInt("table_number");
        id=sharedPreferences.getString(Constants.USER_ID,null);
        mHandler = new Handler(Looper.getMainLooper());

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.packet_example, null);
        busy=(RecyclerView)view.findViewById(R.id.times);
        timer=(Button)view.findViewById(R.id.timer);
        timer1=(Button)view.findViewById(R.id.timer1);
        data=(Button)view.findViewById(R.id.data);
        handler = new Handler(Looper.getMainLooper());
        bisyTables=new ArrayList<>();
        setInitialDateTime();
        ShowABusy();
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), t,
                        DateAndTime.get(Calendar.HOUR_OF_DAY),
                        DateAndTime.get(Calendar.MINUTE), true)
                        .show();

            }
        });
        timer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), t1,
                        DateAndTime.get(Calendar.HOUR_OF_DAY),
                        DateAndTime.get(Calendar.MINUTE), true)
                        .show();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), d,
                        DateAndTime.get(Calendar.YEAR),
                        DateAndTime.get(Calendar.MONTH),
                        DateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
                data.setText(DateUtils.formatDateTime(getActivity(),
                        DateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            }
        });
        builder.setTitle("Заказ столика №"+table_number).setView(view)
                .setPositiveButton("Заказать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (b == true) {
                            String uri = Constants.apilink + "zakaz.php?rest_id=" + id_rest + "&id_user=" + id +
                                    "&number=" + table_number + "&time_with=" + time_with + "&time_after=" + time_after + "&data=" + date_table;

                            OkHttpClient client = new OkHttpClient();
                            //Execute request
                            final Request request = new Request.Builder()
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
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try
                                            {
                                                JSONObject ob = new JSONObject(res);
                                                int id_u=Integer.parseInt(id);
                                                datable.sendMessage(table_number,id_rest,id_u,time_with,time_after,date_table);
                                            }
                                            catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }



    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            DateAndTime.set(Calendar.MINUTE, minute);
            timer.setText(DateUtils.formatDateTime(getActivity(),
                    DateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
            String h=Integer.toString(hourOfDay);
            String m=Integer.toString(minute);
            if(h.length()==1) h="0"+h;
            if(m.length()==1) m="0"+m;
            time_with=h+":"+m;
            tw=DateAndTime.getTimeInMillis();

        }
    };

    TimePickerDialog.OnTimeSetListener t1=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            DateAndTime.set(Calendar.MINUTE, minute);
            timer1.setText(DateUtils.formatDateTime(getActivity(),
                    DateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
            String h=Integer.toString(hourOfDay);
            String m=Integer.toString(minute);
            if(h.length()==1) h="0"+h;
            if(m.length()==1) m="0"+m;
            time_after=h+":"+m;
            tt=DateAndTime.getTimeInMillis();
            if(tt-tw<=0)
            {
                Toast.makeText(getActivity(),"Введите правильно время",Toast.LENGTH_LONG).show();
            }
            else
            {
                b=true;
            }
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateAndTime.set(Calendar.YEAR, year);
            DateAndTime.set(Calendar.MONTH, monthOfYear);
            DateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            data.setText(DateUtils.formatDateTime(getActivity(),
                    DateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            String m=Integer.toString(monthOfYear);
            String day=Integer.toString(dayOfMonth);
            if(m.length()==1) m="0"+m;
            if(day.length()==1) day="0"+day;
            date_table=year+"-"+m+"-"+day;
            ShowABusy();
        }
    };

    private void setInitialDateTime() {
        timer.setText(DateUtils.formatDateTime(getActivity(),
                DateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
        Date date=DateAndTime.getTime();
        time_with=time_after=date.getHours()+":"+date.getMinutes();
        timer1.setText(DateUtils.formatDateTime(getActivity(),
                DateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
        data.setText(DateUtils.formatDateTime(getActivity(),
                DateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        Calendar calendar=Calendar.getInstance();
        date_table=1900+date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datable = (Datable) context;
    }

    public void ShowABusy()
    {
        String uri = Constants.apilink + "isbusy.php?number=" + table_number+"&date="+date_table+"&rest_id="+id_rest;
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
                final JSONObject arr;
                try {
                    arr = new JSONObject(res);
                    JSONArray array=arr.getJSONArray("busy");

                    for (int i = 0; i < array.length(); i++) {
                        String name = array.getJSONObject(i).getString("time_with");
                        String desc = array.getJSONObject(i).getString("time_after");
                        bisyTables.add(new TimeBisyTable(name,desc));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("erro", e.getMessage() + " ");
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!bisyTables.isEmpty()) {
                            adapter = new BusyAdapter(bisyTables);
                            busy.setAdapter(adapter);
                        }
                    }
                });

            }
        });
    }
}
