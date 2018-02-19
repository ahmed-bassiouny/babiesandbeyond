package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 24/12/17.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.MyViewHolder> {

    public List<Task> tasks;
    public Activity activity;

    public TaskItemAdapter(Activity activity, List<Task> tasks) {
        this.tasks = tasks;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserImage;
        private TextView ivTaskDateTime;
        private TextView tvUserName;
        private TextView tvTaskLocation;
        private TextView btnTaskAction;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_group_image);
            ivTaskDateTime = view.findViewById(R.id.iv_task_date_time);
            tvUserName = view.findViewById(R.id.tv_task_name);
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
        final Task task = tasks.get(position);
        holder.tvUserName.setText(task.getUserName());
        holder.ivTaskDateTime.setText(task.getStartDate() + " - " + task.getEndDate());
        holder.tvTaskLocation.setText(task.getLocation());
        if (task.getIsCompleted() && !task.getRate().isEmpty()) {
            holder.btnTaskAction.setVisibility(View.VISIBLE);
            holder.btnTaskAction.setText("Completed");
            holder.btnTaskAction.setBackgroundColor(activity.getResources().getColor(R.color.gray_bold));
        } else if (task.getIsCompleted()) {
            holder.btnTaskAction.setVisibility(View.VISIBLE);
            holder.btnTaskAction.setText("Rate");
            holder.btnTaskAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, RateUserDialogActivity.class);
                    i.putExtra(IntentDataKey.MY_TASK, task.getId());
                    activity.startActivityForResult(i, IntentDataKey.CHANGE_TASK_DATA_CODE);
                }
            });
            holder.btnTaskAction.setBackgroundColor(activity.getResources().getColor(R.color.colorButton));
        } else {
            holder.btnTaskAction.setVisibility(View.INVISIBLE);
        }
        if (!task.getUserPhoto().isEmpty())
            Utils.MyGlide(activity, holder.ivUserImage, task.getUserPhoto());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setRateToTask(int taskId) {
        int taskSize = tasks.size();
        for (int i = 0; i < taskSize; i++) {
            if (tasks.get(i).getId() == taskId) {
                tasks.get(i).setRate("0");
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateTask(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

}