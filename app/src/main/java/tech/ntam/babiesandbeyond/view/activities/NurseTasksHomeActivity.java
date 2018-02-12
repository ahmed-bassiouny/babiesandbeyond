package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.babiesandbeyond.view.adapter.TaskItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

public class NurseTasksHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskItemAdapter adapter;
    protected TextView tvTitle;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_tasks_home);
        tvTitle = findViewById(R.id.toolbar_title);
        recyclerView = findViewById(R.id.recycle_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        tvTitle.setText(R.string.schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.getTasks(this, new BaseResponseInterface<List<Task>>() {
            @Override
            public void onSuccess(List<Task> tasks) {
                adapter = new TaskItemAdapter(NurseTasksHomeActivity.this,tasks);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setEnabled(true);
                dialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(NurseTasksHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setEnabled(false);
                dialog.dismissMyDialog();
            }
        });
        findViewById(R.id.iv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NurseTasksHomeActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
                } else {
                    builder = new AlertDialog.Builder(NurseTasksHomeActivity.this);
                }
                builder.setMessage("Are you sure you want to Logout?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RequestAndResponse.logout(NurseTasksHomeActivity.this, new BaseResponseInterface<String>() {
                                    @Override
                                    public void onSuccess(String s) {
                                    }

                                    @Override
                                    public void onFailed(String errorMessage) {
                                    }
                                });
                                UserSharedPref.clearShared(NurseTasksHomeActivity.this);
                                startActivity(new Intent(NurseTasksHomeActivity.this, SignIn_UpActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getTasks(NurseTasksHomeActivity.this, new BaseResponseInterface<List<Task>>() {
                    @Override
                    public void onSuccess(List<Task> tasks) {
                        adapter.updateTask(tasks);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(NurseTasksHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentDataKey.CHANGE_TASK_DATA_CODE && resultCode == Activity.RESULT_OK && data != null) {
            int taskId = data.getIntExtra(IntentDataKey.MY_TASK,0);
            if (taskId!= 0) {
                adapter.setRateToTask(taskId);
            }
        }
    }
}
