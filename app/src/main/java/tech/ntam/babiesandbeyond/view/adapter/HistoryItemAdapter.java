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
import tech.ntam.babiesandbeyond.model.ServiceFeedback;
import tech.ntam.babiesandbeyond.model.UserHistory;

/**
 * Created by bassiouny on 22/12/17.
 */

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.MyViewHolder> {

    private List<UserHistory> historyList;
    private ParseObjectWithPosition parseObject;
    private Activity activity;
    public HistoryItemAdapter(Activity activity, List<UserHistory> historyList) {
        this.historyList = historyList;
        this.parseObject = (ParseObjectWithPosition) activity;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ivServiceName;
        private TextView tvServiceLocation;
        private TextView tvServiceDateTime;
        private TextView tvType;

        public MyViewHolder(View view) {
            super(view);
            ivServiceName = view.findViewById(R.id.tv_service_name);
            tvServiceLocation = view.findViewById(R.id.tv_location);
            tvServiceDateTime = view.findViewById(R.id.tv_date_time);
            tvType = view.findViewById(R.id.tv_type);
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
        holder.tvType.setText(history.getType());
        holder.ivServiceName.setText(history.getName());
        holder.tvServiceDateTime.setText(history.getHistoryDates().get(0).getFrom());
        holder.tvServiceLocation.setText(history.getComment());
        holder.tvServiceLocation.setText(history.getLocation());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void updateFeed(final ServiceFeedback feedback){
        // get array list size
        final int size = historyList.size();
        // start thread to compare item id with history list
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<size;i++){
                    if(historyList.get(i).getId().equals(feedback.getId())){
                        final int currentPosition = i;
                        UserHistory item = historyList.get(i);
                        item.setRate(feedback.getRate());
                        historyList.set(currentPosition,item);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(currentPosition);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }
}