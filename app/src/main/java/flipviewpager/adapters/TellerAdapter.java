package flipviewpager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myitschool.newtravel.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Teller;
import Util.Constants;

/**
 * Created by 208-it-01 on 12.04.2018.
 */

public class TellerAdapter extends RecyclerView.Adapter<TellerAdapter.ViewHolder>
{
    private ArrayList<Teller> tellers;

    private Context context;
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public TellerAdapter(ArrayList<Teller> messages, Context cont) {
        context=cont;
        tellers=messages;
    }

    @Override
    public TellerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new TellerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TellerAdapter.ViewHolder holder, final int position) {
        Teller teller=tellers.get(position);
        String tel=teller.getDesc();
        String im= Constants.apilink+teller.getUrl();
        holder.name_teller.setText(teller.getName());
        holder.teller.setText(teller.getDesc());
        holder.teller_price.setText(String.format("%.2f",teller.getPrice()));
        if(bitmaps.containsKey(im))
        {
            holder.rest_teller_image.setImageBitmap(bitmaps.get(im));
        }
        else
        {
            new LoadImageTask(holder.rest_teller_image).execute(Constants.apilink+teller.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return tellers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView rest_teller_image;
        public TextView teller;
        private TextView name_teller;
        private TextView teller_price;
        private TextView price;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_view);
            rest_teller_image=(ImageView)itemView.findViewById(R.id.rest_teller_image);
            teller = (TextView) itemView.findViewById(R.id.telller);
            name_teller=(TextView)itemView.findViewById(R.id.name_teller);
            teller_price=(TextView)itemView.findViewById(R.id.telller_price);
            price=(TextView)itemView.findViewById(R.id.price);
        }
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView teller_image; // displays the thumbnail

    // store ImageView on which to set the downloaded Bitmap
    public LoadImageTask(ImageView imageView) {
        this.teller_image = imageView;
    }

    // load image; params[0] is the String URL representing the image
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(params[0]); // create URL for image

            // open an HttpURLConnection, get its InputStream
            // and download the image
            connection = (HttpURLConnection) url.openConnection();

            try (InputStream inputStream = connection.getInputStream()) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect(); // close the HttpURLConnection
        }
        bitmaps.put(params[0],bitmap);
        return bitmap;
    }

    // set weather condition image in list item
    @Override
    protected void onPostExecute(Bitmap bitmap) {teller_image.setImageBitmap(bitmap);
    }
}
}
