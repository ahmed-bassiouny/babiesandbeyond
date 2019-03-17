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

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.interfaces.ParseService;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;

/**
 * Created by Developer on 22/12/17.
 */

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.MyViewHolder> {


    private List<Service> services;
    private Activity activity;
    private ParseService parseService;

    public ServiceItemAdapter(Activity activity, Fragment fragment, List<Service> services) {
        this.services = services;
        this.activity = activity;
        this.parseService = (ParseService) fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceStatus;
        private TextView tvServiceType;
        private TextView tvDateFrom;
        private TextView tvTimeFrom;
        private TextView tvDateTo;
        private TextView tvTimeTo;

        public MyViewHolder(View view) {
            super(view);
            tvServiceStatus = view.findViewById(R.id.tv_service_status);
            tvServiceType = view.findViewById(R.id.tv_service_type);
            tvDateFrom = view.findViewById(R.id.tv_date_from);
            tvTimeFrom = view.findViewById(R.id.tv_time_from);
            tvDateTo = view.findViewById(R.id.tv_date_to_value);
            tvTimeTo = view.findViewById(R.id.tv_time_to);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Service service = services.get(getAdapterPosition());
                    parseService.getService(service);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_service_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Service service = services.get(position);
        holder.tvDateFrom.setText(service.getStartDate());
        holder.tvTimeFrom.setText(service.getStartTime());
        holder.tvDateTo.setText(service.getEndDate());
        holder.tvTimeTo.setText(service.getEndTime());
        holder.tvServiceType.setText(service.getServiceTypeName());
        holder.tvServiceStatus.setBackgroundColor(activity.getResources().getColor(service.getServiceStatusColor()));
        holder.tvServiceStatus.setText(service.getServiceStatusString());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void addService(Service service) {
        services.add(0, service);
        notifyItemInserted(0);
    }

    public void deleteService(final int id) {
        final int size = services.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Service item = services.get(i);
                    if(item.getId() == id){
                        final int position = i;
                        services.remove(i);
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
    public void updateService(final int id, final String price,final String staffName) {
        final int size = services.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Service item = services.get(i);
                    if(item.getId() == id){
                        final int position = i;
                        item.updateServiceStatusName();
                        item.setPrice(price);
                        item.setStaffName(staffName);
                        services.set(i,item);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(position);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }

    public void updateService(final Service service) {
        final int size = services.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Service item = services.get(i);
                    if(item.getId() == service.getId()){
                        final int position = i;
                        services.set(i,service);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(position);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }
    public void updateServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }
}