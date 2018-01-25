package tech.ntam.babiesandbeyond.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by bassiouny on 22/12/17.
 */

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.MyViewHolder> {

    private List<Group> groups;
    private Fragment fragment;
    private GroupOption groupOption;
    private boolean showAddLeaveGroup = false;
    private ParseObject parseObject;
    private Context context;

    public GroupItemAdapter(List<Group> groups, Fragment fragment, Context context) {
        this.groups = groups;
        this.fragment = fragment;
        groupOption = (GroupOption) fragment;
        parseObject = (ParseObject) fragment;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivGroupImage;
        private ImageView ivGroupStatus;
        private TextView tvGroupStatus;
        private TextView tvGroupName;
        private TextView tvGroupCreatedBy;
        private TextView tvDescription;
        private ImageView ivMore;
        private TextView tvDate;
        private ConstraintLayout container;

        public MyViewHolder(View view) {
            super(view);
            ivGroupImage = view.findViewById(R.id.iv_group_image);
            ivGroupStatus = view.findViewById(R.id.iv_group_status);
            tvGroupStatus = view.findViewById(R.id.tv_group_status);
            tvGroupName = view.findViewById(R.id.tv_group_name);
            tvGroupCreatedBy = view.findViewById(R.id.tv_group_created_by);
            tvDescription = view.findViewById(R.id.tv_description);
            ivMore = view.findViewById(R.id.iv_more);
            tvDate = view.findViewById(R.id.tv_date);
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
        //creating a popup menu
        final PopupMenu popup = new PopupMenu(context, holder.ivMore,Gravity.END);
        //inflating menu from xml resource
        popup.inflate(R.menu.group_menu);

        holder.tvGroupName.setText(group.getName());
        holder.tvGroupCreatedBy.setText("Created by " + group.getCreatedByName());
        holder.tvDescription.setText(group.getDescription());
        holder.tvDate.setText(group.getDate());
        if (!group.getPhoto().isEmpty())
            Utils.MyGlide(fragment.getActivity(), holder.ivGroupImage, group.getPhoto());


        if(group.getStatus()){
            // group approved
            if(group.getUserStatus().equals(Constant.USER_PENDING_GROUP)){
                holder.ivMore.setVisibility(View.INVISIBLE);
                holder.ivGroupStatus.setImageDrawable(context.getDrawable(R.drawable.pendingjoin));
                holder.tvGroupStatus.setText(Constant.USER_PENDING_GROUP_TEXT);
            }else if (group.getUserStatus().equals(Constant.USER_IN_GROUP)){
                holder.ivMore.setVisibility(View.VISIBLE);
                popup.getMenu().findItem(R.id.join).setVisible(false);
                popup.getMenu().findItem(R.id.leave).setVisible(true);
                holder.ivGroupStatus.setImageDrawable(context.getDrawable(R.drawable.joined));
                holder.tvGroupStatus.setText(Constant.USER_IN_GROUP_TEXT);
            }else {
                holder.ivMore.setVisibility(View.VISIBLE);
                popup.getMenu().findItem(R.id.join).setVisible(true);
                popup.getMenu().findItem(R.id.leave).setVisible(false);
                holder.ivGroupStatus.setVisibility(View.INVISIBLE);
                holder.tvGroupStatus.setVisibility(View.INVISIBLE);
            }
        }else{
            // group pending
            holder.tvGroupStatus.setText(Constant.USER_PENDING_APPROVAL_GROUP_TEXT);
            holder.ivGroupStatus.setImageDrawable(context.getDrawable(R.drawable.pending));
            holder.ivMore.setVisibility(View.INVISIBLE);
        }

        /*if (group.getStatus()) {
            holder.ivMore.setVisibility(View.VISIBLE);
        } else {
            holder.ivMore.setVisibility(View.INVISIBLE);
            holder.tvGroupStatus.setText(Constant.USER_PENDING_APPROVAL_GROUP_TEXT);
        }
        if (group.getUserStatus().equals(Constant.USER_PENDING_GROUP)) {
            holder.ivMore.setVisibility(View.INVISIBLE);
            holder.tvGroupStatus.setText(Constant.USER_PENDING_GROUP_TEXT);
        } else if (group.getUserStatus().equals(Constant.USER_IN_GROUP)){
            popup.getMenu().findItem(R.id.join).setVisible(false);
            popup.getMenu().findItem(R.id.leave).setVisible(true);
            holder.tvGroupStatus.setText(Constant.USER_IN_GROUP_TEXT);
        }else {
            popup.getMenu().findItem(R.id.join).setVisible(true);
            popup.getMenu().findItem(R.id.leave).setVisible(false);
            holder.tvGroupStatus.setVisibility(View.INVISIBLE);
        }*/
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.join:
                                groupOption.JoinGroup(group.getId(), position);
                                break;
                            case R.id.leave:
                                groupOption.LeaveGroup(group.getId(), position);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

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

    public void updateList(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        notifyItemInserted(groups.size() - 1);
    }
}