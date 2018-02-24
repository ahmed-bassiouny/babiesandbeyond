package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class MidwifeItemAdapter extends RecyclerView.Adapter<MidwifeItemAdapter.MyViewHolder> {

    private List<Midwife> midwifeList;
    private Activity activity;
    private ParseObject<Midwife> parseObject;
    public MidwifeItemAdapter(Activity activity,List<Midwife> midwifeList) {
        this.midwifeList = midwifeList;
        this.activity = activity;
        parseObject = (ParseObject<Midwife>) activity;
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
                    parseObject.getMyObject(item);
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

}