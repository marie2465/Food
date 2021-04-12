package flipviewpager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myitschool.newtravel.R;

import java.util.ArrayList;

import Models.TimeBisyTable;

public class BusyAdapter extends RecyclerView.Adapter<BusyAdapter.ViewHolder>
{
    private ArrayList<TimeBisyTable> timeBisyTables;

    public BusyAdapter(ArrayList<TimeBisyTable> timeBisyTables) {
        this.timeBisyTables = timeBisyTables;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_item, parent, false);
        return new BusyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeBisyTable tb=timeBisyTables.get(position);
        String time_wis=tb.getTime_with();
        String time_avter=tb.getTime_after();
        holder.time_with.setText(time_wis);
        holder.time_after.setText(time_avter);
    }

    @Override
    public int getItemCount() {
        return timeBisyTables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time_with;
        private TextView time_after;

        public ViewHolder(View itemView) {
            super(itemView);
            time_with=(TextView)itemView.findViewById(R.id.texttime_with);
            time_after=(TextView)itemView.findViewById(R.id.texttime_after);
        }
    }
}
