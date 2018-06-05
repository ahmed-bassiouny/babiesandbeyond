package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.Task;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

public class RateUserDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar;
    private EditText etComment;
    private TextView tvWriteNote,tvRate;
    private UserHistory userHistory;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user_dialog);
        ratingBar = findViewById(R.id.rating_bar);
        etComment = findViewById(R.id.et_comment);
        tvWriteNote = findViewById(R.id.tv_write_note);
        tvRate = findViewById(R.id.tv_rate);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        getData();
        onCLick();
    }

    private void onCLick() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRate.setText(getRateString(rating));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if(!UserSharedPref.isStaff(this)){
                    if(ratingBar.getRating()==0){
                        Toast.makeText(this, "Please Enter Rate", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // send rate
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(this);
                    RequestAndResponse.userRateService(this, userHistory.isMidwife(), userHistory.getId(), (int)ratingBar.getRating(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(RateUserDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            userHistory.setRate(ratingBar.getRating());
                            ServiceSharedPref.setUserHistory(RateUserDialogActivity.this, userHistory);
                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(RateUserDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                        }
                    });
                }else {
                    // send comment
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(this);
                    RequestAndResponse.userCommentService(this, task.getId(), etComment.getText().toString(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(RateUserDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            task.setComment(etComment.getText().toString());
                            ServiceSharedPref.setTask(RateUserDialogActivity.this, task);
                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(RateUserDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                        }
                    });
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
    private void getData(){
        userHistory = ServiceSharedPref.getUserHistory(this);
        task = ServiceSharedPref.getMyTask(this);
        if(UserSharedPref.isStaff(this)){
            ratingBar.setVisibility(View.GONE);
            tvRate.setVisibility(View.GONE);
            etComment.setVisibility(View.VISIBLE);
            tvWriteNote.setVisibility(View.VISIBLE);
        }else {
            ratingBar.setVisibility(View.VISIBLE);
            tvRate.setVisibility(View.VISIBLE);
            etComment.setVisibility(View.GONE);
            tvWriteNote.setVisibility(View.GONE);
        }
    }

    public String getRateString(float num) {
        switch ((int)num){
            case 1:return "Very Bad";
            case 2:return "Bad";
            case 3:return "Good";
            case 4:return "Very Good";
            case 5:return "Excellent";
            default:return "";
        }
    }

}
