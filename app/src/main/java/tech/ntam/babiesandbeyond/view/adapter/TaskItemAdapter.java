package tech.ntam.babiesandbeyond.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;

/**
 * Created by bassiouny on 24/12/17.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.MyViewHolder> {

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}