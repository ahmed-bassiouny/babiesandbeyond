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
import tech.ntam.babiesandbeyond.model.MessageAdmin;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by Developer on 22/12/17.
 */

public class MessageAdminItemAdapter extends RecyclerView.Adapter<MessageAdminItemAdapter.MyViewHolder> {

    private List<MessageAdmin> messageAdmins;
    private String userId;

    public MessageAdminItemAdapter(List<MessageAdmin> messageAdmins, String userId) {
        this.messageAdmins = messageAdmins;
        this.userId = userId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage;
        private TextView tvDate;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tv_message);
            tvDate = view.findViewById(R.id.tv_date);
            tvName = view.findViewById(R.id.tv_name);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message_admin_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageAdmin item = messageAdmins.get(position);
        holder.tvMessage.setText(item.getMessage());
        holder.tvDate.setText(item.getDate());
        if (item.getFromId().equals(userId))
            holder.tvName.setText(Constant.ME);
        else
            holder.tvName.setText(Constant.ADMIN);

    }

    @Override
    public int getItemCount() {
        return messageAdmins.size();
    }

    public void updateList(List<MessageAdmin> messageAdmins) {
        this.messageAdmins = messageAdmins;
        notifyDataSetChanged();
    }

    public void addMessage(MessageAdmin item) {
        this.messageAdmins.add(0, item);
        notifyItemInserted(0);
    }
}