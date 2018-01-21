package tech.ntam.babiesandbeyond.view.adapter;

import android.content.Context;
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
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;

/**
 * Created by bassiouny on 22/12/17.
 */

public class MidwifeItemAdapter extends RecyclerView.Adapter<MidwifeItemAdapter.MyViewHolder> {


    public MidwifeItemAdapter() {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       /* private TextView tvServiceStatus;
        private TextView tvServiceType;
        private TextView tvDateFrom;
        private TextView tvTimeFrom;
        private TextView tvDateTo;
        private TextView tvTimeTo;
        private ImageView imageView;*/

        public MyViewHolder(View view) {
            super(view);/*
            tvServiceStatus = view.findViewById(R.id.tv_service_status);
            tvServiceType = view.findViewById(R.id.tv_service_type);
            tvDateFrom = view.findViewById(R.id.tv_date_from);
            tvTimeFrom = view.findViewById(R.id.tv_time_from);
            tvDateTo = view.findViewById(R.id.tv_date_to_value);
            tvTimeTo = view.findViewById(R.id.tv_time_to);
            imageView = view.findViewById(R.id.imageView);*/
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
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}