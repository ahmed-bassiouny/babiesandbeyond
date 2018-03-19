package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.Utils;

public class showTaskDetailsActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserLocation;
    private TextView tvUserRateText;
    private TextView tvUserRate;
    private TextView tvCommentText;
    private TextView tvComment;
    private Button btnSendComment;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_details);
        setupToolbar(this,false,true,false);
        tvTitle.setText("Task Info");
        findView();
    }

    private void findView() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserPhone = findViewById(R.id.tv_user_phone);
        tvUserLocation = findViewById(R.id.tv_user_location);
        tvUserRateText = findViewById(R.id.tv_user_rate_text);
        tvUserRate = findViewById(R.id.tv_user_rate);
        tvCommentText = findViewById(R.id.tv_comment_text);
        tvComment = findViewById(R.id.tv_comment);
        btnSendComment = findViewById(R.id.btn_send_comment);
        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSharedPref.setTask(showTaskDetailsActivity.this,task);
                startActivity(new Intent(showTaskDetailsActivity.this, RateUserDialogActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData(){
        task = ServiceSharedPref.getMyTask(this);
        if(task == null)
            finish();
        tvUserName.setText(task.getUserName());
        tvUserLocation.setText(task.getLocation());
        tvUserPhone.setText(task.getUserPhone());
        if(task.getComment().isEmpty()) {
            tvComment.setText("None");
            btnSendComment.setVisibility(View.VISIBLE);
        }
        else {
            tvComment.setText(task.getComment());
            btnSendComment.setVisibility(View.GONE);
        }
        if(task.getRateString().isEmpty()){
            tvUserRate.setVisibility(View.GONE);
            tvUserRateText.setVisibility(View.GONE);
        } else {
            tvUserRate.setText(task.getRateString());
        }
        if(!task.getUserPhoto().isEmpty())
            Utils.MyGlide(this,ivProfilePhoto,task.getUserPhoto());
    }
}
