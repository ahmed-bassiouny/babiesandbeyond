package tech.ntam.babiesandbeyond.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Message;

/**
 * Created by bassiouny on 15/01/18.
 */

public class ItemChatAdapter extends RecyclerView.Adapter<ItemChatAdapter.CutomViewHolder> {
    List<Message> myarraylist;
    Context context;

    public ItemChatAdapter(List<Message> myarraylist, Context context) {
        this.context = context;
        this.myarraylist = myarraylist;
    }

    @Override
    public ItemChatAdapter.CutomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_item, null);
        CutomViewHolder cutomViewHolder = new CutomViewHolder(view);
        return cutomViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemChatAdapter.CutomViewHolder holder, int position) {

        Message message = myarraylist.get(position);
        holder.message.setText(message.getMessage());

        if(message.isPhoto()){
            holder.ivPhoto.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        }else {
            holder.ivPhoto.setVisibility(View.GONE);
            holder.message.setVisibility(View.VISIBLE);
        }
        if (message.isMe()) {
            holder.image.setVisibility(View.GONE);
            holder.message.setBackgroundResource(R.drawable.rounded_blue_chat_message);
            holder.container.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            holder.image.setVisibility(View.VISIBLE);
            holder.message.setBackgroundResource(R.drawable.rounded_gray_chat_message);
            holder.container.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    @Override
    public int getItemCount() {
        return myarraylist.size();
    }

    class CutomViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        RelativeLayout container;
        ImageView image,ivPhoto;

        public CutomViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.tvt_msg);
            container = view.findViewById(R.id.container);
            image = view.findViewById(R.id.image);
            ivPhoto = view.findViewById(R.id.iv_photo);
        }
    }
    public void addMessage(Message message){
        myarraylist.add(message);
        notifyItemInserted(myarraylist.size()-1);
    }
}