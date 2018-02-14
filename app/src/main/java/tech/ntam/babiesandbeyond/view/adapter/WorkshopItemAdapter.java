package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import tech.ntam.babiesandbeyond.model.Workshop;

/**
 * Created by bassiouny on 22/12/17.
 */

public class WorkshopItemAdapter extends RecyclerView.Adapter<WorkshopItemAdapter.MyViewHolder> {


    private List<Workshop> workshops;
    private ParseObject parseObject;
    private Activity activity;

    public WorkshopItemAdapter(Activity activity, Fragment fragment, List<Workshop> workshops) {
        this.workshops = workshops;
        this.parseObject = (ParseObject) fragment;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceStatus;
        private TextView tvServiceType;
        private TextView tvDateFrom;
        private TextView tvTimeFrom;
        private TextView tvDateTo;
        private TextView tvTimeTo;
        private TextView tvTextType;
        private ConstraintLayout constraintLayout;

        public MyViewHolder(View view) {
            super(view);
            tvServiceStatus = view.findViewById(R.id.tv_service_status);
            tvServiceType = view.findViewById(R.id.tv_service_type);
            tvDateFrom = view.findViewById(R.id.tv_date_from);
            tvTimeFrom = view.findViewById(R.id.tv_time_from);
            tvDateTo = view.findViewById(R.id.tv_date_to_value);
            tvTimeTo = view.findViewById(R.id.tv_time_to);
            tvTextType = view.findViewById(R.id.textView2);
            constraintLayout = view.findViewById(R.id.constraint);
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
        final Workshop workshop = workshops.get(position);
        holder.tvTextType.setText(R.string.workshop);
        holder.tvDateFrom.setText(workshop.getStartDate());
        holder.tvTimeFrom.setText(workshop.getStartTime());
        holder.tvDateTo.setText(workshop.getEndDate());
        holder.tvTimeTo.setText(workshop.getEndTime());
        holder.tvServiceType.setText(workshop.getName());
        holder.tvServiceStatus.setText(workshop.getWorkshopStatusName());
        if (!workshop.getWorkshopStatusName().isEmpty()) {
            holder.tvServiceStatus.setVisibility(View.VISIBLE);
            holder.tvServiceStatus.setBackgroundColor(activity.getResources().getColor(workshop.getWorkshopStatusColor()));
        } else {
            holder.tvServiceStatus.setVisibility(View.GONE);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseObject.getMyObject(workshop);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workshops.size();
    }

    public void updateWorkshop(Workshop workshop) {
        int workshopsLenght = workshops.size();
        for (int i = 0; i < workshopsLenght; i++) {
            if (workshop.getWorkshopId() == workshops.get(i).getId()) {
                workshops.get(i).setComing(true);
                workshops.get(i).setWorkshopStatusName(workshop.getWorkshopStatusName());
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateWorkshopToConfirmationWithoutPayment(final int id) {
        final int size = workshops.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Workshop item = workshops.get(i);
                    if (item.getId() == id) {
                        final int position = i;
                        item.updateWorkshopToConfirmationWithoutPayment();
                        workshops.set(i, item);
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
    public void deleteWorkshop(final int id) {
        final int size = workshops.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Workshop item = workshops.get(i);
                    if(item.getId() == id){
                        final int position = i;
                        workshops.remove(i);
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
    public void updateWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
        notifyDataSetChanged();
    }
}