package flipviewpager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myitschool.newtravel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Models.Otziv;
import Util.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class VitrinaAdapter extends RecyclerView.Adapter<VitrinaAdapter.ViewHolder>
{
    private ArrayList<Otziv> vitrinas;
    private String v;
    private int id;
    private Context context;
    public VitrinaAdapter(ArrayList<Otziv> vitrinas, Context mes) {
        this.vitrinas = vitrinas;
        context=mes;
    }

    @Override
    public VitrinaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vitrina, parent, false);
        return new VitrinaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Otziv vt=vitrinas.get(position);
        holder.id_message.setText(vt.getMessage());
        holder.name_us.setText(vt.getName());
        String path=Constants.apilink+"uploads/IMG_"+vt.getId_user()+".jpg";
        Picasso.with(context).load(path).into(holder.pro_foto);
        holder.liks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.disliks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return vitrinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_message;
        private ImageView pro_foto;
        private TextView name_us;
        private CircleImageView liks;
        private CircleImageView disliks;
        public ViewHolder(View itemView) {
            super(itemView);
            id_message=(TextView) itemView.findViewById(R.id.vit);
            pro_foto=(ImageView)itemView.findViewById(R.id.pro_foto);
            name_us=(TextView)itemView.findViewById(R.id.name_us);
            liks=(CircleImageView)itemView.findViewById(R.id.liks);
            disliks=(CircleImageView)itemView.findViewById(R.id.disliks);
        }
    }
}