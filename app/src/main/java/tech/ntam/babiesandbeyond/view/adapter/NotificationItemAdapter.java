package tech.ntam.babiesandbeyond.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.DummyNotificationItem;

/**
 * Created by bassiouny on 22/12/17.
 */

public class NotificationItemAdapter extends RecyclerView.Adapter<NotificationItemAdapter.MyViewHolder> {

    List<DummyNotificationItem> dummyNotificationItems;

    public NotificationItemAdapter(List<DummyNotificationItem> dummyNotificationItems) {
        this.dummyNotificationItems = dummyNotificationItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNotificationTitle;
        private TextView tvNotificationDateTime;
        private TextView tvPrice;
        private Button btnPay;

        public MyViewHolder(View view) {
            super(view);
            tvNotificationTitle = view.findViewById(R.id.tv_notification_title);
            tvNotificationDateTime = view.findViewById(R.id.tv_notification_date_time);
            tvPrice = view.findViewById(R.id.tv_price);
            btnPay = view.findViewById(R.id.btn_pay);
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
        DummyNotificationItem dummyNotificationItem = dummyNotificationItems.get(position);
        holder.tvNotificationTitle.setText(dummyNotificationItem.title);
        holder.tvNotificationDateTime.setText(dummyNotificationItem.date);
        if (dummyNotificationItem.price.isEmpty()) {
            holder.btnPay.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.INVISIBLE);

        } else {
            holder.btnPay.setVisibility(View.INVISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(dummyNotificationItem.price);

        }
    }

    @Override
    public int getItemCount() {
        return dummyNotificationItems.size();
    }
}