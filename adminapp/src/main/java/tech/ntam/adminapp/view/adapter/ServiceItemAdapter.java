package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
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
import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/12/17.
 */

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.MyViewHolder> {
    private List<Service> services;
    private ParseTasks parseTasks;
    private Fragment fragment;


    public ServiceItemAdapter(Fragment fragment, List<Service> services) {
        parseTasks = (ParseTasks) fragment;
        this.services = services;
        this.fragment = fragment;
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
            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Service item = services.get(getAdapterPosition());
                    parseTasks.viewService(item);
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
        Service service = services.get(position);
        if (!service.getPhoto().isEmpty())
            Utils.MyGlide(fragment.getActivity(), holder.ivUserImage, service.getPhone());
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