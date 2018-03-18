package tech.ntam.babiesandbeyond.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Notification;

/**
 * Created by bassiouny on 22/12/17.
 */

public class NotificationItemAdapter extends RecyclerView.Adapter<NotificationItemAdapter.MyViewHolder> {

    List<Notification> notifications;

    public NotificationItemAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNotificationTitle;
        private TextView tvNotificationDateTime;

        public MyViewHolder(View view) {
            super(view);
            tvNotificationTitle = view.findViewById(R.id.tv_notification_title);
            tvNotificationDateTime = view.findViewById(R.id.tv_notification_date_time);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.tvNotificationTitle.setText(notification.getNotification());
        holder.tvNotificationDateTime.setText(notification.getNotificationTime());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}