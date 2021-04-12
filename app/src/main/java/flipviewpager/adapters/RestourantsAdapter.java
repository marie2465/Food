package flipviewpager.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myitschool.newtravel.R;
import com.myitschool.newtravel.restourant.activity.RestourantsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Restourant;
import Util.Constants;

/**
 * Created by 208-it-01 on 28.03.2018.
 */

public class RestourantsAdapter extends RecyclerView.Adapter<RestourantsAdapter.ViewHolder>{
    private ArrayList<Restourant> restourants;
    private Context context;
    private Map<String, Bitmap> bitmaps = new HashMap<>();
    public RestourantsAdapter(ArrayList<Restourant> messages,Context cont) {
        this.restourants = messages;
        context=cont;
    }

    @Override
    public RestourantsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restoraunt_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RestourantsAdapter.ViewHolder holder, final int position) {
        Restourant restourant=restourants.get(position);
        String res=restourant.getTitle();
        String im=Constants.apilink+restourant.getImage_url_main();
        holder.textViewName.setText(restourant.getTitle());
        holder.kuxnia.setText(restourant.getComment());
        if(bitmaps.containsKey(im))
        {
            holder.rest.setImageBitmap(bitmaps.get(im));
        }
        else
        {
            Picasso.with(context).load(Constants.apilink+restourant.getImage_url_main()).into(holder.rest);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, RestourantsActivity.class);
                intent.putParcelableArrayListExtra(RestourantsActivity.EXTRA_REST,restourants);
                intent.putExtra(RestourantsActivity.EXTRA_NUM,position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restourants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView rest;
        public TextView textViewName;
        public TextView kuxnia;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_view);
            rest=(ImageView)itemView.findViewById(R.id.RestImage);
            textViewName = (TextView) itemView.findViewById(R.id.textRes);
            kuxnia=(TextView)itemView.findViewById(R.id.kuxnia);
        }
    }
}