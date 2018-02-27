package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.interfaces.ParseService;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by bassiouny on 22/12/17.
 */

public class MidwifeServiceItemAdapter extends RecyclerView.Adapter<MidwifeServiceItemAdapter.MyViewHolder> {


    private List<MidwifeService> services;
    private Activity activity;
    private ParseService parseService;

    public MidwifeServiceItemAdapter(Activity activity, Fragment fragment, List<MidwifeService> services) {
        this.services = services;
        this.activity = activity;
        this.parseService = (ParseService) fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceStatus;
        private TextView tvMidwifeName;

        public MyViewHolder(View view) {
            super(view);
            tvServiceStatus = view.findViewById(R.id.tv_midwife_status);
            tvMidwifeName = view.findViewById(R.id.tv_midwife_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MidwifeService service = services.get(getAdapterPosition());
                    parseService.getMidwife(service);

                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_midwife_service_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MidwifeService service = services.get(position);
        holder.tvMidwifeName.setText(service.getMidwifeName());
        holder.tvServiceStatus.setText(service.getMidwifeStatus());
        holder.tvServiceStatus.setBackgroundColor(activity.getResources().getColor(service.getMidwifeStatusColor()));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

   /* public void addService(Service service) {
        services.add(0, service);
        notifyItemInserted(0);
    }
*/
    public void deleteService(final String uniqueKey) {
        final int size = services.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    MidwifeService item = services.get(i);
                    if(item.getUniqueKey().equals(uniqueKey)){
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
    public void updateService(final String uniqueKey) {
        final int size = services.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    MidwifeService item = services.get(i);
                    if(item.getUniqueKey().equals(uniqueKey)){
                        final int position = i;
                        item.setMidwifeStatus(Constant.CONFIRMATION_WITHOUT_PAYMENT);
                        services.set(position,item);
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

    public void insertService(final MidwifeService midwifeService) {
        services.add(midwifeService);
        notifyItemChanged(services.size()-1);
    }
    public void updateMidwifeServices(List<MidwifeService> midwifeServices) {
        this.services = midwifeServices;
        notifyDataSetChanged();
    }
}