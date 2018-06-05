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
import tech.ntam.adminapp.model.MidwifeService;
import tech.ntam.adminapp.view.activities.MidwifeRequestAndDetailsActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/12/17.
 */

public class MidwifeRequestItemAdapter extends RecyclerView.Adapter<MidwifeRequestItemAdapter.MyViewHolder> {

    private List<MidwifeService> midwifeList;
    private Activity activity;
    private ParseMidwife parseMidwife;

    public MidwifeRequestItemAdapter(Activity activity, Fragment fragment, List<MidwifeService> midwifeList) {
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
                    MidwifeService item = midwifeList.get(getAdapterPosition());
                    parseMidwife.assignmentMidwife(item,getAdapterPosition());
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
        MidwifeService item = midwifeList.get(position);
        holder.tvName.setText(item.getUserName());
        if(!item.getUserPhoto().isEmpty())
            Utils.MyGlide(activity,holder.ivProfilePhoto,item.getUserPhoto());
    }

    @Override
    public int getItemCount() {
        return midwifeList.size();
    }

    public void updateRequests(List<MidwifeService> midwifeList){
        this.midwifeList = midwifeList;
        notifyDataSetChanged();
    }
    public void addRequest(MidwifeService midwifeService){
        this.midwifeList.add(0,midwifeService);
        notifyItemInserted(0);
    }
    public void deleteService(final String uniqueKey) {
        final int size = midwifeList.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    MidwifeService item = midwifeList.get(i);
                    if(item.getUniqueKey().equals(uniqueKey)){
                        final int position = i;
                        midwifeList.remove(i);
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