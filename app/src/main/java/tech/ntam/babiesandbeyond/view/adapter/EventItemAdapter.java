package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.MyViewHolder> {


    private List<Event> events;
    private ParseObject parseObject;
    private Activity activity;

    public EventItemAdapter(Activity activity, Fragment fragment, List<Event> events) {
        this.events = events;
        this.parseObject = (ParseObject) fragment;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceStatus;
        private TextView tvServiceType;
        private TextView tvDateFrom;
        private TextView tvTimeFrom;
        private TextView tvDateTo;
        private TextView tvTimeTo;
        private TextView tvTextType;

        public MyViewHolder(View view) {
            super(view);
            tvServiceStatus = view.findViewById(R.id.tv_service_status);
            tvServiceType = view.findViewById(R.id.tv_service_type);
            tvDateFrom = view.findViewById(R.id.tv_date_from);
            tvTimeFrom = view.findViewById(R.id.tv_time_from);
            tvDateTo = view.findViewById(R.id.tv_date_to_value);
            tvTimeTo = view.findViewById(R.id.tv_time_to);
            tvTextType = view.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event event = events.get(getAdapterPosition());
                    parseObject.getMyObject(event);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_service_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = events.get(position);
        holder.tvTextType.setText(R.string.event_name);
        holder.tvDateFrom.setText(event.getStartDate());
        holder.tvTimeFrom.setText(event.getStartTime());
        holder.tvDateTo.setText(event.getEndDate());
        holder.tvTimeTo.setText(event.getEndTime());
        holder.tvServiceType.setText(event.getName());
        holder.tvServiceStatus.setText(event.getName());
        if (event.isComing()) {
            holder.tvServiceStatus.setText(R.string.coming);
            holder.tvServiceStatus.setVisibility(View.VISIBLE);
            holder.tvServiceStatus.setBackgroundColor(activity.getResources().getColor(R.color.gray_bold));
        } else {
            holder.tvServiceStatus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateEvent(Event event) {
        int eventsLenght = events.size();
        for (int i = 0; i < eventsLenght; i++) {
            if (event.getId() == events.get(i).getId()) {
                events.get(i).setComing(event.isComing());
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public void deleteEvent(final int id) {
        final int size = events.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Event item = events.get(i);
                    if (item.getId() == id) {
                        final int position = i;
                        events.remove(i);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemRemoved(position);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }
}