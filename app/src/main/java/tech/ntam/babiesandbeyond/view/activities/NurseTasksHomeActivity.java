package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.adapter.TaskItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class NurseTasksHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskItemAdapter adapter;
    protected TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_tasks_home);
        tvTitle = findViewById(R.id.toolbar_title);
        recyclerView = findViewById(R.id.recycle_view);

        tvTitle.setText(R.string.schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.getTasks(this, new BaseResponseInterface<List<Task>>() {
            @Override
            public void onSuccess(List<Task> tasks) {
                adapter = new TaskItemAdapter(NurseTasksHomeActivity.this,tasks);
                recyclerView.setAdapter(adapter);
                dialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(NurseTasksHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });
        findViewById(R.id.iv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(NurseTasksHomeActivity.this);
                RequestAndResponse.logout(NurseTasksHomeActivity.this, new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(NurseTasksHomeActivity.this, s, Toast.LENGTH_SHORT).show();
                        UserSharedPref.clearShared(NurseTasksHomeActivity.this);
                        myDialog.dismissMyDialog();
                        startActivity(new Intent(NurseTasksHomeActivity.this, SignIn_UpActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(NurseTasksHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
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
