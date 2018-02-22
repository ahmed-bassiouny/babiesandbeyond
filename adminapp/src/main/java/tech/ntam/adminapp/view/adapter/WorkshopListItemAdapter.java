package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.interfaces.ParseTasks;
import tech.ntam.adminapp.interfaces.ParseWorkshop;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.adminapp.view.activities.WorkshopDetailsActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class WorkshopListItemAdapter extends RecyclerView.Adapter<WorkshopListItemAdapter.MyViewHolder> {
    private List<Workshop> workshops;
    private Fragment fragment;
    private ParseWorkshop parseWorkshop;

    public WorkshopListItemAdapter(Fragment fragment, List<Workshop> workshops) {
        this.fragment = fragment;
        this.workshops = workshops;
        this.parseWorkshop = (ParseWorkshop) fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvWorkshopName;
        private TextView tvWorkshopLocation;
        private Button btnViewDetails;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvWorkshopName = view.findViewById(R.id.tv_service_name);
            tvWorkshopLocation = view.findViewById(R.id.tv_service_reserved);
            btnViewDetails = view.findViewById(R.id.btn_view_details);
            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Workshop item = workshops.get(getAdapterPosition());
                    parseWorkshop.viewWorkshop(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_service_nurse_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Workshop workshop= workshops.get(position);
        holder.tvWorkshopName.setText(workshop.getName());
        holder.tvWorkshopLocation.setText(workshop.getLocation());
    }

    @Override
    public int getItemCount() {
        return workshops.size();
    }

    public void updateWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
        notifyDataSetChanged();
    }
}