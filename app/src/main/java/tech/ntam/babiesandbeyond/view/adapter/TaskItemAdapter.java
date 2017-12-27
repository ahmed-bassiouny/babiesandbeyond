package tech.ntam.babiesandbeyond.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.mylibrary.DummyTaskModel;

/**
 * Created by bassiouny on 24/12/17.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.MyViewHolder> {

    public List<DummyTaskModel> dummyTaskModels;
    public Context context;

    public TaskItemAdapter(Context context, List<DummyTaskModel> dummyTaskModels) {
        this.dummyTaskModels = dummyTaskModels;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView ivGroupImage;
        private TextView ivTaskDateTime;
        private TextView tvTaskName;
        private TextView tvTaskLocation;
        private Button btnTaskAction;

        public MyViewHolder(View view) {
            super(view);
            ivGroupImage = view.findViewById(R.id.iv_group_image);
            ivTaskDateTime = view.findViewById(R.id.iv_task_date_time);
            tvTaskName = view.findViewById(R.id.tv_task_name);
            tvTaskLocation = view.findViewById(R.id.tv_task_location);
            btnTaskAction = view.findViewById(R.id.btn_task_action);
        }
    }

    @Override
    public TaskItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_task_item, parent, false);
        return new TaskItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskItemAdapter.MyViewHolder holder, int position) {
        DummyTaskModel dummyTaskModel = dummyTaskModels.get(position);
        holder.tvTaskName.setText(dummyTaskModel.name);
        holder.ivTaskDateTime.setText(dummyTaskModel.time);
        holder.tvTaskLocation.setText(dummyTaskModel.location);
        if (dummyTaskModel.version == 1) {
            holder.btnTaskAction.setVisibility(View.VISIBLE);
            holder.btnTaskAction.setText("Complete");
            holder.btnTaskAction.setBackgroundColor(context.getResources().getColor(R.color.gray_bold));
        } else if (dummyTaskModel.version == 2) {
            holder.btnTaskAction.setVisibility(View.VISIBLE);
            holder.btnTaskAction.setText("Rate");
            holder.btnTaskAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, RateUserDialogActivity.class));
                }
            });
            holder.btnTaskAction.setBackgroundColor(context.getResources().getColor(R.color.colorButton));
        } else {
            holder.btnTaskAction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dummyTaskModels.size();
    }
}