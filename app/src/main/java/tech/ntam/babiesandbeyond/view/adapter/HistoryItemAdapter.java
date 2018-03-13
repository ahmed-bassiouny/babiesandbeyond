package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.interfaces.ParseObjectWithPosition;
import tech.ntam.babiesandbeyond.model.History;
import tech.ntam.babiesandbeyond.model.UserHistory;

/**
 * Created by bassiouny on 22/12/17.
 */

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.MyViewHolder> {

    private List<UserHistory> historyList;
    private ParseObjectWithPosition parseObject;
    public HistoryItemAdapter(Activity activity, List<UserHistory> historyList) {
        this.historyList = historyList;
        this.parseObject = (ParseObjectWithPosition) activity;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parseObject.getMyObject(historyList.get(getAdapterPosition()),getAdapterPosition());
                }
            });
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
        UserHistory history = historyList.get(position);
        holder.ivServiceName.setText(history.getName());
        //holder.tvServiceDateTime.setText(history.getStartDate()+"\n"+history.getEndDate());
        holder.tvServiceLocation.setText(history.getComment());
        //holder.tvServicePrice.setText(history.getPrice());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void updateRate(String rate,int position){
        UserHistory item = historyList.get(position);
        item.setRate(rate);
        historyList.set(position,item);
        notifyItemChanged(position);
    }
    public void updateComment(String comment,int position){
        UserHistory item = historyList.get(position);
        item.setComment(comment);
        historyList.set(position,item);
        notifyItemChanged(position);
    }
}