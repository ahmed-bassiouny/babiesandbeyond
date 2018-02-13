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
import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.MyViewHolder> {
    private List<Service> services;
    private Activity activity;

    public ServiceItemAdapter(Activity activity, List<Service> services) {
        this.activity = activity;
        this.services = services;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvServiceName;
        private TextView tvServiceReserved;
        private Button btnViewDetails;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvServiceName = view.findViewById(R.id.tv_service_name);
            tvServiceReserved = view.findViewById(R.id.tv_service_reserved);
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
        Service service = services.get(position);
        if (!service.getPhoto().isEmpty())
            Utils.MyGlide(activity, holder.ivUserImage, service.getPhone());
        holder.tvServiceName.setText(service.getName());
        holder.tvServiceReserved.setText(service.isReserved());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void updateService(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }
}