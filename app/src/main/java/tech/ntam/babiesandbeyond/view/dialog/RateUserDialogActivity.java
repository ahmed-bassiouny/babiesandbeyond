package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.ServiceFeedback;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

public class RateUserDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar;
    private EditText etComment;
    private ServiceFeedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user_dialog);
        ratingBar = findViewById(R.id.rating_bar);
        etComment = findViewById(R.id.et_comment);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
      /*  ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1)
                    ratingBar.setRating(1);
            }
        });*/
        getData();
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
                    RequestAndResponse.userRateService(this,feedback.isMidwife(), feedback.getId(), ratingBar.getRating(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(RateUserDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(IntentDataKey.RATE, String.valueOf(ratingBar.getRating()));
                            setResult(Activity.RESULT_OK, resultIntent);
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
                    RequestAndResponse.userCommentService(this,feedback.isMidwife(),feedback.getId(), etComment.getText().toString(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(RateUserDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(IntentDataKey.COMMENT, etComment.getText().toString());
                            setResult(Activity.RESULT_OK, resultIntent);
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
        feedback = getIntent().getParcelableExtra(IntentDataKey.FEEDBACK);
        if (feedback  == null)
            finish();
        if(UserSharedPref.isStaff(this)){
            ratingBar.setEnabled(false);
            etComment.setEnabled(true);
        }else {
            ratingBar.setEnabled(true);
            etComment.setEnabled(false);
        }
        ratingBar.setRating(feedback.getRate());
        etComment.setText(feedback.getComment());
    }
}
