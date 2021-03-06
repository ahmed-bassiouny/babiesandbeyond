package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.MidwifeRequestInterface;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.view.activities.RequestMidwifeActivity;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * Created by Developer on 22/12/17.
 */

public class RequestMidwifeItemAdapter extends RecyclerView.Adapter<RequestMidwifeItemAdapter.MyViewHolder> {


    private List<MidwifeRequestModel> midwifeRequestModels;
    private MidwifeRequestInterface anInterface;

    public RequestMidwifeItemAdapter(Activity activity) {
        this.midwifeRequestModels = new ArrayList<>();
        anInterface = (MidwifeRequestInterface) activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRequestDay;
        private TextView tvRequestDate;
        private TextView tvFrom;
        private TextView tvTo;
        private ImageView ivDelete;

        public MyViewHolder(View view) {
            super(view);
            tvRequestDay = view.findViewById(R.id.tv_request_day);
            tvRequestDate = view.findViewById(R.id.tv_request_date);
            tvFrom = view.findViewById(R.id.tv_from);
            tvTo = view.findViewById(R.id.tv_to);
            ivDelete = view.findViewById(R.id.iv_delete);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anInterface.removeRequest(midwifeRequestModels.get(getAdapterPosition()));
                    midwifeRequestModels.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anInterface.editRequest(midwifeRequestModels.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_midwife_request_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MidwifeRequestModel item = midwifeRequestModels.get(position);
        holder.tvRequestDay.setText(item.getDay());
        holder.tvRequestDate.setText(item.getDate());
        holder.tvFrom.setText(item.getTimeFrom12H());
        holder.tvTo.setText(item.getTimeTo12H());

    }

    @Override
    public int getItemCount() {
        return midwifeRequestModels.size();
    }

    public void insertItem(MidwifeRequestModel midwifeRequestModel){
        midwifeRequestModels.add(midwifeRequestModel);
        notifyItemInserted(midwifeRequestModels.size()-1);
    }
    public void updateItem(MidwifeRequestModel midwifeRequestModel,int position){
        midwifeRequestModels.set(position,midwifeRequestModel);
        notifyItemChanged(position);
    }
    public List<MidwifeRequestModel> getList(){
        return midwifeRequestModels;
    }
    public MidwifeRequestModel getItem(int position){
        return midwifeRequestModels.get(position);
    }

}