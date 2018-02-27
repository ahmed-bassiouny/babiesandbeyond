package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.interfaces.ParseMidwife;
import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.view.activities.MidwifeRequestAndDetailsActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class MidwifeItemListAdapter extends RecyclerView.Adapter<MidwifeItemListAdapter.MyViewHolder> {

    private List<Midwife> midwifeList;
    private ParseMidwife parseMidwife;
    private Activity activity;
    public MidwifeItemListAdapter(Activity activity, Fragment fragment, List<Midwife> midwifeList) {
        this.midwifeList = midwifeList;
        this.activity = activity;
        this.parseMidwife = (ParseMidwife) fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivProfilePhoto;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            ivProfilePhoto = view.findViewById(R.id.iv_profile_photo);
            tvName = view.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Midwife item = midwifeList.get(getAdapterPosition());
                    parseMidwife.viewMidwife(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_midwife_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Midwife item = midwifeList.get(position);
        holder.tvName.setText(item.getName());
        if(!item.getPhoto().isEmpty())
            Utils.MyGlide(activity,holder.ivProfilePhoto,item.getPhoto());
    }

    @Override
    public int getItemCount() {
        return midwifeList.size();
    }


    public void updateList(List<Midwife> midwifeList){
        this.midwifeList = midwifeList;
        notifyDataSetChanged();
    }

}