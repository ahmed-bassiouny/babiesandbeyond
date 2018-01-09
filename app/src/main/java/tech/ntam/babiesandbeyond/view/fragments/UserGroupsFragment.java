package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.database.GroupsDatabase;
import tech.ntam.babiesandbeyond.interfaces.GroupOption;
import tech.ntam.babiesandbeyond.view.activities.CreateGroupActivity;
import tech.ntam.babiesandbeyond.view.adapter.GroupItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserGroupsFragment extends Fragment implements GroupOption {


    private SegmentedGroup segmentedGroup;
    private RadioButton btnAllGroups;
    private RadioButton btnMostPopular;
    private RadioButton btnMyGroups;
    private TextView tvCreateGroup;
    private RecyclerView recycleView;
    private GroupItemAdapter groupItemAdapter;
    private static UserGroupsFragment userGroupsFragment;


    public UserGroupsFragment() {
        // Required empty public constructor
    }


    public static UserGroupsFragment newInstance() {
        if (userGroupsFragment == null) {
            userGroupsFragment = new UserGroupsFragment();
        }
        return userGroupsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        segmentedGroup = view.findViewById(R.id.segmented2);
        btnAllGroups = view.findViewById(R.id.btn_all_groups);
        btnMostPopular = view.findViewById(R.id.btn_most_popular);
        btnMyGroups = view.findViewById(R.id.btn_my_groups);
        recycleView = view.findViewById(R.id.recycle_view);
        tvCreateGroup = view.findViewById(R.id.tv_greate_group);
        setData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getContext() != null && isVisibleToUser) {
            MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
            titleToolbar.setTitleToolbar(getString(R.string.groups));
        }
    }


    private void setData() {
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupItemAdapter = new GroupItemAdapter(GroupsDatabase.getGroups(), this);
        recycleView.setAdapter(groupItemAdapter);
        tvCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateGroupActivity.class));
            }
        });
    }

    @Override
    public void JoinGroup(int groupId, final int position) {
        MyDialog.showMyDialog(getContext());
        RequestAndResponse.joinGroup(getContext(), groupId, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                groupItemAdapter.notifyItemChanged(position);
                MyDialog.dismissMyDialog();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                MyDialog.dismissMyDialog();
            }
        });
    }

    @Override
    public void LeaveGroup(int groupId, final int position) {
        MyDialog.showMyDialog(getContext());
        RequestAndResponse.joinGroup(getContext(), groupId, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                groupItemAdapter.notifyItemChanged(position);
                MyDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                MyDialog.dismissMyDialog();
            }
        });
    }
}
