package tech.ntam.babiesandbeyond.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.History;

/**
 * Created by bassiouny on 22/12/17.
 */

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.MyViewHolder> {

    List<History> historyList;

    public HistoryItemAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ivServiceName;
        private TextView tvServiceLocation;
        private TextView tvServiceDateTime;
        private TextView tvServicePrice;

        public MyViewHolder(View view) {
            super(view);
            ivServiceName = view.findViewById(R.id.tv_service_name);
            tvServiceLocation = view.findViewById(R.id.tv_location);
            tvServiceDateTime = view.findViewById(R.id.tv_date_time);
            tvServicePrice = view.findViewById(R.id.tv_price);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_history_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.ivServiceName.setText(history.getName());
        holder.tvServiceDateTime.setText(history.getStartDate()+" "+history.getEndDate());
        holder.tvServiceLocation.setText(history.getLocation());
        holder.tvServicePrice.setText(history.getPrice());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}