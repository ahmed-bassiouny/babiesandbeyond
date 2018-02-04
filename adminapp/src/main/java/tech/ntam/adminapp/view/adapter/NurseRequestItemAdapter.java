package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class NurseRequestItemAdapter extends RecyclerView.Adapter<NurseRequestItemAdapter.MyViewHolder> {
    private boolean showActionButton;
    private List<Service> services;
    private Activity activity;

    public NurseRequestItemAdapter(Activity activity, boolean showActionButton, List<Service> services) {
        this.activity = activity;
        this.showActionButton = showActionButton;
        this.services = services;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvDate;
        private TextView tvRequestName;
        private TextView tvRequestLocation;
        private Button btnViewDetails;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvDate = view.findViewById(R.id.tv_date);
            tvRequestName = view.findViewById(R.id.tv_request_name);
            tvRequestLocation = view.findViewById(R.id.tv_request_location);
            btnViewDetails = view.findViewById(R.id.btn_view_details);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_request_nurse_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Service service = services.get(position);
        if (showActionButton) {
            holder.btnViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.btnViewDetails.setVisibility(View.INVISIBLE);
        }
        if (!service.getUserPhoto().isEmpty())
            Utils.MyGlide(activity, holder.ivUserImage, service.getUserPhoto());
        holder.tvDate.setText(service.getStartDate() + " " + service.getStartTime() + " to " + service.getEndTime());
        holder.tvRequestName.setText(service.getUserName());
        holder.tvRequestLocation.setText(service.getLocation());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}