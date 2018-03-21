package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.view.activities.NurseTasksHomeActivity;
import tech.ntam.babiesandbeyond.view.activities.showTaskDetailsActivity;
import tech.ntam.babiesandbeyond.view.adapter.TaskItemAdapter;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseScheduleFragment extends Fragment implements ParseObject<Task> {


    private static NurseScheduleFragment nurseScheduleFragment;
    private RecyclerView recyclerView;
    private TaskItemAdapter scheduleAdapter;
    private TaskItemAdapter doneAdapter;
    private RadioButton btnSchedule;
    private RadioButton btnDone;
    private List<Task> completedTask;
    private List<Task> scheduleTask;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isViewShown = false;


    public NurseScheduleFragment() {
        // Required empty public constructor
    }

    public static NurseScheduleFragment newInstance() {
        if (nurseScheduleFragment == null)
            nurseScheduleFragment = new NurseScheduleFragment();
        return nurseScheduleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        onClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataNurse();
    }

    private void onClick() {
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scheduleAdapter != null)
                    recyclerView.setAdapter(scheduleAdapter);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneAdapter != null)
                    recyclerView.setAdapter(doneAdapter);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataNurse();
            }
        });
    }

    private void loadDataNurse() {
        if (scheduleAdapter != null && doneAdapter != null) {
            if (btnSchedule.isChecked()) {
                recyclerView.setAdapter(scheduleAdapter);
            } else {
                recyclerView.setAdapter(doneAdapter);
            }
            return;
        }

        completedTask = new ArrayList<>();
        scheduleTask = new ArrayList<>();
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(getActivity());
        RequestAndResponse.getTasks(getActivity(), new BaseResponseInterface<List<Task>>() {
            @Override
            public void onSuccess(final List<Task> tasks) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (Task item : tasks) {
                            if (item.isCompleted()) {
                                completedTask.add(item);
                            } else {
                                scheduleTask.add(item);
                            }
                        }
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    doneAdapter = new TaskItemAdapter(NurseScheduleFragment.this, completedTask);
                                    scheduleAdapter = new TaskItemAdapter(NurseScheduleFragment.this, scheduleTask);
                                    recyclerView.setAdapter(scheduleAdapter);
                                    dialog.dismissMyDialog();
                                }
                            });
                    }
                }).start();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });
    }

    private void findView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
        btnSchedule = view.findViewById(R.id.btn_schedule);
        btnDone = view.findViewById(R.id.btn_done);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void getMyObject(Task task) {
        ServiceSharedPref.setTask(getContext(), task);
        startActivity(new Intent(getContext(), showTaskDetailsActivity.class));
    }

    private void refreshDataNurse() {
        swipeRefreshLayout.setRefreshing(true);
        RequestAndResponse.getTasks(getActivity(), new BaseResponseInterface<List<Task>>() {
            @Override
            public void onSuccess(final List<Task> tasks) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        completedTask = new ArrayList<>();
                        scheduleTask = new ArrayList<>();
                        for (Task item : tasks) {
                            if (item.isCompleted()) {
                                completedTask.add(item);
                            } else {
                                scheduleTask.add(item);
                            }
                        }
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (doneAdapter == null || scheduleAdapter == null) {
                                        doneAdapter = new TaskItemAdapter(NurseScheduleFragment.this, completedTask);
                                        scheduleAdapter = new TaskItemAdapter(NurseScheduleFragment.this, scheduleTask);
                                    } else {
                                        doneAdapter.updateTask(completedTask);
                                        scheduleAdapter.updateTask(scheduleTask);
                                    }
                                    recyclerView.setAdapter(scheduleAdapter);
                                    btnSchedule.setChecked(true);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                    }
                }).start();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
