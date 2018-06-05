package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.view.adapter.TaskTimeSlotsAdapter;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;

public class showTaskDetailsActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserLocation, tvViewUserLocation;
    private TextView tvUserRate;
    private TextView tvFrom,tvFromText;
    private TextView tvTo,tvToText;
    private TextView tvComment;
    private Button btnSendComment;
    private RecyclerView recyclerView;
    private Task task;
    private RelativeLayout dateContain;
    private TextView date, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_details);
        setupToolbar(this, false, true, false);
        tvTitle.setText("Task Info");
        findView();
    }

    private void findView() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserPhone = findViewById(R.id.tv_user_phone);
        tvUserLocation = findViewById(R.id.tv_user_location);
        tvFrom = findViewById(R.id.tv_from);
        tvFromText = findViewById(R.id.tv_from_text);
        tvUserRate = findViewById(R.id.tv_user_rate);
        tvTo = findViewById(R.id.tv_to);
        tvToText = findViewById(R.id.tv_to_text);
        tvComment = findViewById(R.id.tv_comment);
        btnSendComment = findViewById(R.id.btn_send_comment);
        recyclerView = findViewById(R.id.recycler);
        dateContain = findViewById(R.id.date_contain);
        date = findViewById(R.id.tv_day_date);
        day = findViewById(R.id.tv_day_name);
        tvViewUserLocation = findViewById(R.id.tv_view_user_location);
        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSharedPref.setTask(showTaskDetailsActivity.this, task);
                startActivity(new Intent(showTaskDetailsActivity.this, RateUserDialogActivity.class));
            }
        });
        tvViewUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", task.getLatitude(), task.getLongitude());
                String geoUri = "http://maps.google.com/maps?q=loc:" + task.getLatitude() + "," + task.getLongitude()   ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        task = ServiceSharedPref.getMyTask(this);
        if (task == null)
            finish();
        tvUserName.setText(task.getUserName());
        tvUserPhone.setText(task.getUserPhone());
        if (!task.getLocation().isEmpty())
            tvUserLocation.setText(task.getLocation());

        if (!task.getComment().isEmpty()) {
            btnSendComment.setVisibility(View.GONE);
            tvComment.setText(task.getComment());
        }

        if (!task.getRateString().isEmpty())
            tvUserRate.setText(task.getRateString());

        if (!task.getUserPhoto().isEmpty())
            Utils.MyGlide(this, ivProfilePhoto, task.getUserPhoto());
        // if user nurse or babysitter
        if (!UserSharedPref.getIamMidwife(this)) {
            tvFrom.setText(task.getDates().get(0).getFullStartDate());
            tvTo.setText(task.getDates().get(0).getFullEndDate());
        } else {
            // user is midwife
            dateContain.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            tvFromText.setVisibility(View.GONE);
            tvFrom.setVisibility(View.GONE);
            tvToText.setVisibility(View.GONE);
            tvTo.setVisibility(View.GONE);
            date.setText(task.getDates().get(0).getDate());
            day.setText(MyDateTimeFactor.convertDateStringToDayOfWeek(task.getDates().get(0).getDate()));
            TaskTimeSlotsAdapter adapter = new TaskTimeSlotsAdapter(task.getDates());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
        if (task.getLatitudeDouble()!=0 && task.getLongitudeDouble()!=0) {
            tvViewUserLocation.setVisibility(View.VISIBLE);
        }
    }
}
