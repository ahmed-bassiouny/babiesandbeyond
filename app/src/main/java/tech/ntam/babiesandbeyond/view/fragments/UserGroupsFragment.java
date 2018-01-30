package tech.ntam.babiesandbeyond.view.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.GroupOption;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.ChatActivity;
import tech.ntam.babiesandbeyond.view.activities.CreateGroupActivity;
import tech.ntam.babiesandbeyond.view.adapter.GroupItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserGroupsFragment extends Fragment implements GroupOption, ParseObject<Group> {


    private RadioButton btnAllGroups;
    private RadioButton btnMostPopular;
    private RadioButton btnMyGroups;
    private TextView tvCreateGroup;
    private RecyclerView recycleView;
    private GroupItemAdapter groupItemAdapter;
    private static UserGroupsFragment userGroupsFragment;
    private List<Group> allGroups;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;

    private LinearLayout container;

    private boolean isViewShown = false;

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
        btnAllGroups = view.findViewById(R.id.btn_all_groups);
        btnMostPopular = view.findViewById(R.id.btn_most_popular);
        btnMyGroups = view.findViewById(R.id.btn_my_groups);
        recycleView = view.findViewById(R.id.recycle_view);
        tvCreateGroup = view.findViewById(R.id.tv_greate_group);
        progress = view.findViewById(R.id.progress);
        noInternet = view.findViewById(R.id.no_internet);
        btnNoInternet = view.findViewById(R.id.btn_no_internet);
        container = view.findViewById(R.id.container);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        onCLick();
        if (!isViewShown) {
            loadGroup();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadGroup();
        } else {
            isViewShown = false;
        }
    }


    private void onCLick() {
        tvCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CreateGroupActivity.class), IntentDataKey.ADD_GROUP_DATA_CODE);
            }
        });
        btnAllGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupItemAdapter.updateList(allGroups);
            }
        });
        btnMyGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(getActivity());
                int myId = UserSharedPref.getId(getContext());
                List<Group> myGroup = new ArrayList<>();
                for (Group item : allGroups) {
                    if (item.getCreatedBy() == myId) {
                        myGroup.add(item);
                    }

                }
                groupItemAdapter.updateList(myGroup);
                myDialog.dismissMyDialog();
            }
        });
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGroup();
            }
        });
    }

    @Override
    public void JoinGroup(int groupId, final int position) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getActivity());
        RequestAndResponse.joinGroup(getContext(), groupId, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                groupItemAdapter.addLeaveGroup(position, Constant.USER_PENDING_GROUP);
                myDialog.dismissMyDialog();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }

    @Override
    public void LeaveGroup(int groupId, final int position) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getActivity());
        RequestAndResponse.leaveGroup(getContext(), groupId, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                groupItemAdapter.addLeaveGroup(position, Constant.USER_OUT_GROUP);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }

    private void loadGroup() {
        // check if adapter is null that mean i don't load data from backend
        // if adapter not equal null that mean i loaded data so i set it in recycler view
        if (groupItemAdapter != null) {
            recycleView.setAdapter(groupItemAdapter);
            progress.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
            btnNoInternet.setVisibility(View.GONE);

            return;
        }
        progress.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        noInternet.setVisibility(View.GONE);
        btnNoInternet.setVisibility(View.GONE);

        RequestAndResponse.getGroups(getContext(), new BaseResponseInterface<List<Group>>() {
            @Override
            public void onSuccess(final List<Group> groups) {
                final int myId = UserSharedPref.getId(getContext());
                allGroups = new ArrayList<>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Group item : groups) {
                            if (item.getCreatedBy() == myId) {
                                allGroups.add(item);
                            } else if (item.getStatus()) {
                                allGroups.add(item);
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                groupItemAdapter = new GroupItemAdapter(allGroups, UserGroupsFragment.this, getActivity());
                                recycleView.setAdapter(groupItemAdapter);
                                progress.setVisibility(View.GONE);
                                container.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onFailed(String errorMessage) {
                progress.setVisibility(View.GONE);
                noInternet.setVisibility(View.VISIBLE);
                btnNoInternet.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void getMyObject(Group group) {
        if (group.getUserStatus().equals(Constant.USER_OUT_GROUP)) {
            Toast.makeText(getContext(), R.string.please_join_group, Toast.LENGTH_SHORT).show();
        } else if (group.getUserStatus().equals(Constant.USER_IN_GROUP)) {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra(IntentDataKey.SHOW_GROUP_DATA_KEY, group);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), R.string.group_pending, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentDataKey.ADD_GROUP_DATA_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Group group = data.getParcelableExtra(IntentDataKey.ADD_GROUP_DATA_KEY);
            if (group != null) {
                allGroups.add(group);
                groupItemAdapter.addGroup(group);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_GROUP));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String groupId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
            if (groupId == null || groupId.isEmpty())
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            switch (action) {
                case "0": //this case mean group approved
                    groupItemAdapter.approvedGroup(Integer.parseInt(groupId));
                    break;
                case "1": //this case mean group cancel
                case "5": //this case mean group delete
                    groupItemAdapter.deleteGroup(Integer.parseInt(groupId));
                    break;
                case "2": //this case mean user approve joined
                    groupItemAdapter.updateUserStatusGroup(Integer.parseInt(groupId), Constant.USER_IN_GROUP);
                    break;
                case "3": //this case mean user reject joined
                case "4": //this case mean user deleted
                    groupItemAdapter.updateUserStatusGroup(Integer.parseInt(groupId), Constant.USER_OUT_GROUP);
                    break;
            }

        }
    };

}
