package tech.ntam.babiesandbeyond.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.HistoryDate;
import tech.ntam.babiesandbeyond.model.Notification;

/**
 * Created by Developer on 22/12/17.
 */

public class TaskTimeSlotsAdapter extends RecyclerView.Adapter<TaskTimeSlotsAdapter.MyViewHolder> {

    List<HistoryDate> historyDates;

    public TaskTimeSlotsAdapter(List<HistoryDate> historyDates) {
        this.historyDates = historyDates;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFrom;
        private TextView tvTo;

        public MyViewHolder(View view) {
            super(view);
            tvFrom = view.findViewById(R.id.tv_from);
            tvTo = view.findViewById(R.id.tv_to);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_midwife_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HistoryDate item = historyDates.get(position);
        holder.tvFrom.setText(item.getFromTime());
        holder.tvTo.setText(item.getToTime());
    }

    @Override
    public int getItemCount() {
        return historyDates.size();
    }

}