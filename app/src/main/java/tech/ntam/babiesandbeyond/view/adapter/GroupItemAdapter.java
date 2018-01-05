package tech.ntam.babiesandbeyond.view.adapter;

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
import tech.ntam.babiesandbeyond.model.Group;

/**
 * Created by bassiouny on 22/12/17.
 */

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.MyViewHolder> {
    List<Group> groups;

    public GroupItemAdapter(List<Group> groups) {
        this.groups = groups;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivGroupImage;
        private TextView ivGroupStatus;
        private TextView tvGroupName;
        private TextView tvGroupCreatedBy;
        private TextView tvDescription;
        private ImageView ivMore;
        private TextView tvDate;
        private Button btnAddLeaveGroup;

        public MyViewHolder(View view) {
            super(view);
            ivGroupImage = view.findViewById(R.id.iv_group_image);
            ivGroupStatus = view.findViewById(R.id.iv_group_status);
            tvGroupName = view.findViewById(R.id.tv_group_name);
            tvGroupCreatedBy = view.findViewById(R.id.tv_group_created_by);
            tvDescription = view.findViewById(R.id.tv_description);
            ivMore = view.findViewById(R.id.iv_more);
            tvDate = view.findViewById(R.id.tv_date);
            btnAddLeaveGroup = view.findViewById(R.id.btn_add_leave_group);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_group_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.ivGroupStatus.setText(group.getStatus());
        holder.tvGroupName.setText(group.getName());
        holder.tvGroupCreatedBy.setText(group.getCreatedBy());
        holder.tvDescription.setText(group.getDescription());
        holder.tvDate.setText(group.getDate());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}