package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class WorkshopListItemAdapter extends RecyclerView.Adapter<WorkshopListItemAdapter.MyViewHolder> {
    private List<Workshop> workshops;
    private Activity activity;

    public WorkshopListItemAdapter(Activity activity, List<Workshop> workshops) {
        this.activity = activity;
        this.workshops = workshops;
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
}