package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 24/12/17.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.MyViewHolder> {

    public List<Task> tasks;
    public Fragment fragment;
    private ParseObject parseObject;

    public TaskItemAdapter(Fragment fragment, List<Task> tasks) {
        this.tasks = tasks;
        this.fragment = fragment;
        parseObject = (ParseObject) fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserImage;
        private TextView ivTaskDateTime;
        private TextView tvUserName;
        private TextView tvTaskLocation;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_group_image);
            ivTaskDateTime = view.findViewById(R.id.iv_task_date_time);
            tvUserName = view.findViewById(R.id.tv_task_name);
            tvTaskLocation = view.findViewById(R.id.tv_task_location);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = tasks.get(getAdapterPosition());
                    parseObject.getMyObject(task);
                }
            });
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
        Task task = tasks.get(position);
        holder.tvUserName.setText(task.getUserName());
        holder.ivTaskDateTime.setText(task.getDates().get(0).getFullStartDate());
        holder.tvTaskLocation.setText(task.getLocation());
        if (!task.getUserPhoto().isEmpty())
            Utils.MyGlide(fragment.getActivity(), holder.ivUserImage, task.getUserPhoto());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setRateToTask(int taskId) {
        int taskSize = tasks.size();
        for (int i = 0; i < taskSize; i++) {
            if (tasks.get(i).getId().equals(String.valueOf(taskId))) {
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

    public void updateTaskComment(final String taskId, final String taskComment) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int taskSize = tasks.size();
                for (int i = 0; i < taskSize; i++) {
                    if (tasks.get(i).getId().equals(taskId)) {
                        Task item = tasks.get(i);
                        item.setComment(taskComment);
                        tasks.set(i, item);
                        final int position = i;
                        fragment.getActivity().runOnUiThread(new Runnable() {
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

}