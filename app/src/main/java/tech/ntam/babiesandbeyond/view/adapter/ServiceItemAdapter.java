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

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.MyViewHolder> {


    private List<Service> services;
    private Context context;
    private ParseObject parseObject;
    public ServiceItemAdapter(Context context, Fragment fragment, List<Service> services) {
        this.services = services;
        this.context=context;
        this.parseObject =(ParseObject)fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceStatus;
        private TextView tvServiceType;
        private TextView tvDateFrom;
        private TextView tvTimeFrom;
        private TextView tvDateTo;
        private TextView tvTimeTo;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            tvServiceStatus = view.findViewById(R.id.tv_service_status);
            tvServiceType = view.findViewById(R.id.tv_service_type);
            tvDateFrom = view.findViewById(R.id.tv_date_from);
            tvTimeFrom = view.findViewById(R.id.tv_time_from);
            tvDateTo = view.findViewById(R.id.tv_date_to_value);
            tvTimeTo = view.findViewById(R.id.tv_time_to);
            imageView = view.findViewById(R.id.imageView);
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
        holder.tvServiceType.setText(ServiceTypeList.getServiceTypeNameFromId(service.getServiceTypeId()));
        holder.tvServiceStatus.setText(service.getServiceStatusString());
        holder.tvServiceStatus.setBackgroundColor(context.getResources().getColor(service.getServiceStatusColor()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseObject.getMyObject(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void addService(Service service){
        services.add(0,service);
        notifyItemInserted(0);
    }
}