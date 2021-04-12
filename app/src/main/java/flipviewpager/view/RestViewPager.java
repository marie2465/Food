package flipviewpager.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.myitschool.newtravel.restourant.activity.MapsActivity;
import com.myitschool.newtravel.restourant.activity.PacketActivity;
import com.myitschool.newtravel.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import Models.Teller;
import Util.APIService;
import Util.Constants;
import Util.ImageManager;
import de.hdodenhof.circleimageview.CircleImageView;
import flipviewpager.adapters.TellerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestViewPager extends Fragment {
    private int count;
    private String title;
    private String comment;
    private String time_start;
    private String time_stop;
    private float lat;
    private float lng;
    private int id;
    private ImageView image_rest;
    private TextView cafe;
    private TextView comment1;
    private RecyclerView menu;
    private ImageButton zakaz;
    private ImageButton togo;
    public  ArrayList<Bitmap> bitmaps;
    private ArrayList<String> images;
    private TellerAdapter adapter;
    private Bitmap bm;
    private MaterialDialog dialog;
    private Handler mHandler;
    private ArrayList<Teller>  tellers;
    private int Busness;
    private String telephone;
    private String info;
    private String url;
    private int TableOnline;
    private int Vitrina;
    private int MenuOnline;
    private int Bancket;
    private int DayAnshlag;
    private int MoreClients;
    private ImageView inform;

    public static RestViewPager newInstance(int count,String title,String comment,int id,float lat, float lng,int Busness,int TableOnline,int Vitrina,int MenuOnline,int Bancket,int DayAnshlag,int MoreClients,String telephone,
                                            String time_start ,String time_stop,String info,String url) {
        RestViewPager fragment = new RestViewPager();
        Bundle args=new Bundle();
        args.putInt("count", count);
        args.putString("title",title);
        args.putString("comment",comment);
        args.putInt("id",id);
        args.putFloat("lat",lat);
        args.putFloat("lng",lng);
        args.putInt("Busness",Busness);
        args.putInt("TableOnline",TableOnline);
        args.putInt("Vitrina",Vitrina);
        args.putInt("MenuOnline",MenuOnline);
        args.putInt("Bancket",Bancket);
        args.putInt("DayAnshlag",DayAnshlag);
        args.putInt("MoreClients",MoreClients);
        args.putString("telephone",telephone);
        args.putString("time_start",time_start);
        args.putString("time_stop",time_stop);
        args.putString("info",info);
        args.putString("url",url);
        fragment.setArguments(args);
        return fragment;
    }

    public RestViewPager() {
        bitmaps=new ArrayList<Bitmap>();
        images=new ArrayList<String>();
        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            count= getArguments().getInt("count");
            title= getArguments().getString("title");
            comment= getArguments().getString("comment");
            id= getArguments().getInt("id");
            lat=getArguments().getFloat("lat");
            lng=getArguments().getFloat("lng");
            Busness=getArguments().getInt("Busness");
            TableOnline=getArguments().getInt("TableOnline");
            Vitrina=getArguments().getInt("Vitrina");
            MenuOnline=getArguments().getInt("MenuOnline");
            Bancket=getArguments().getInt("Bancket");
            DayAnshlag=getArguments().getInt("DayAnshlag");
            MoreClients=getArguments().getInt("MoreClients");
            telephone=getArguments().getString("telephone");
            info=getArguments().getString("info");
            url=getArguments().getString("url");
            time_start=getArguments().getString("time_start");
            time_stop=getArguments().getString("time_stop");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pager_view, container, false);
        image_rest=(ImageView) view.findViewById(R.id.images_rest);

        inform=(ImageView)view.findViewById(R.id.inform);

        cafe=(TextView)view.findViewById(R.id.cafe);
        comment1=(TextView)view.findViewById(R.id.comment1);
        menu= (RecyclerView) view.findViewById(R.id.TellersMenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        menu.setLayoutManager(layoutManager);
//        zakaz=(ImageButton) view.findViewById(R.id.zakaz);
        togo=(ImageButton) view.findViewById(R.id.toGo);
        cafe.setText(title);
//        comment1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/Acquest-Script.ttf"));
        comment1.setText(comment);
        image_rest.setImageBitmap(null);
        image_rest.invalidate();
        GetImages(id);
        GetTellers(id);
        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PacketActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("Busness",Busness);
                intent.putExtra("TableOnline",TableOnline);
                intent.putExtra("Vitrina",Vitrina);
                intent.putExtra("MenuOnline",MenuOnline);
                intent.putExtra("Bancket",Bancket);
                intent.putExtra("DayAnshlag",DayAnshlag);
                intent.putExtra("MoreClients",MoreClients);
                intent.putExtra("phone",telephone);
                intent.putExtra("comment",comment);
                intent.putExtra("count",count);
                intent.putExtra("time_start",time_start);
                intent.putExtra("time_stop",time_stop);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        togo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("title",title);
                startActivity(intent);

            }
        });
        return view;
    }

    public void GetImages(int id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.apilink)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ArrayList<String>> call = service.getImages(id);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                images.addAll(response.body());
                fetchArray(images,image_rest);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    public void GetTellers(int id)
    {
        dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.app_name)
                .content("Fetching trips...")
                .progress(true, 0)
                .show();
        String uri = Constants.apilink + "teller.php?id=" + id;
        Log.e("executing", uri + " ");
        tellers=new ArrayList<>();
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

                final JSONArray arr;
                try {
                    arr = new JSONArray(res);

                    for (int i = 0; i < arr.length(); i++) {
                        String name=arr.getJSONObject(i).getString("name");
                        String desc=arr.getJSONObject(i).getString("desc");
                        String url=arr.getJSONObject(i).getString("url");
                        String price=arr.getJSONObject(i).getString("price");
                        Teller teller=new Teller(name,desc,url,price,null,null,null,null,null,null,null,null,null,null);
                        tellers.add(teller);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("erro", e.getMessage() + " ");
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        adapter=new TellerAdapter(tellers,getActivity());
                        menu.setAdapter(adapter);
                    }
                });

            }
        });
    }

    public void fetchArray(final ArrayList<String> iUrl, final ImageView iView) {
        if ( iUrl == null)
            return;
        final ArrayList<Bitmap> bitmaps=new ArrayList<>();
        final Handler handler = new Handler();

        final Thread thread = new Thread() {
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();
                for (int i = 0; i < iUrl.size(); i++) {
                    Bitmap image = ImageManager.downloadImage(Constants.apilink+iUrl.get(i));
                    bitmaps.add(image);
                }
                for (int i = 0; i < bitmaps.size(); i++) {
                    final int idx = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iView.setImageBitmap(bitmaps.get(idx));
                        }
                    }, i*2000);
                }
            }
        };
        thread.setPriority(3);
        thread.start();
    }
}