package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import tech.ntam.babiesandbeyond.interfaces.GroupOption;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/12/17.
 */

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.MyViewHolder> {

    private List<Group> groups;
    private Fragment fragment;
    private GroupOption groupOption;
    private boolean showAddLeaveGroup = false;
    private ParseObject parseObject;

    public GroupItemAdapter(List<Group> groups, Fragment fragment) {
        this.groups = groups;
        this.fragment = fragment;
        groupOption = (GroupOption) fragment;
        parseObject = (ParseObject) fragment;
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
        private ConstraintLayout container;

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
            container = view.findViewById(R.id.container);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_group_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Group group = groups.get(position);
        holder.ivGroupStatus.setText(group.getStatusString());
        holder.tvGroupName.setText(group.getName());
        holder.tvGroupCreatedBy.setText(group.getCreatedByName());
        holder.tvDescription.setText(group.getDescription());
        holder.tvDate.setText(group.getDate());
        if (!group.getPhoto().isEmpty())
            Utils.MyGlide(fragment.getActivity(), holder.ivGroupImage, group.getPhoto());
        if (group.getStatus()) {
            holder.ivMore.setVisibility(View.VISIBLE);
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showAddLeaveGroup) {
                        holder.btnAddLeaveGroup.setVisibility(View.INVISIBLE);
                        showAddLeaveGroup = false;
                    } else {
                        holder.btnAddLeaveGroup.setVisibility(View.VISIBLE);
                        showAddLeaveGroup = true;
                    }
                }
            });
        } else {
            holder.ivMore.setVisibility(View.INVISIBLE);
        }
        if (group.isMember()) {
            holder.btnAddLeaveGroup.setText(R.string.leave_group);
            holder.btnAddLeaveGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupOption.LeaveGroup(group.getId(), position);
                }
            });
        } else {
            holder.btnAddLeaveGroup.setText(R.string.join_group);
            holder.btnAddLeaveGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupOption.JoinGroup(group.getId(), position);
                }
            });
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseObject.getMyObject(group);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void leaveGroup(int position) {
        Group group = groups.get(position);
        group.setMember(false);
        notifyItemChanged(position);
    }
    public void updateList(List<Group> groups){
        this.groups = groups;
        notifyDataSetChanged();
    }
    public void addGroup(Group group){
        this.groups.add(group);
        notifyItemInserted(groups.size()-1);
    }
}