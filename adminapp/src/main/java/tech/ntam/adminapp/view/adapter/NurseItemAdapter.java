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
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/12/17.
 */

public class NurseItemAdapter extends RecyclerView.Adapter<NurseItemAdapter.MyViewHolder> {
    private List<Service> services;
    private Activity activity;


    public NurseItemAdapter(Activity activity, List<Service> services) {
        this.services = services;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvNurseName;
        private TextView tvEmail;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvNurseName = view.findViewById(R.id.tv_nurse_name);
            tvEmail = view.findViewById(R.id.tv_email);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_nurse_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Service service = services.get(position);
        if (!service.getPhoto().isEmpty())
            Utils.MyGlide(activity, holder.ivUserImage, service.getPhone());
        holder.tvNurseName.setText(service.getName());
        holder.tvEmail.setText(service.getEmail());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

}