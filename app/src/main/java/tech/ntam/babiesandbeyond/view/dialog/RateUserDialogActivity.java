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
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

public class RateUserDialogActivity extends AppCompatActivity implements View.OnClickListener {
    //private RatingBar ratingBar;
    private EditText etComment;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user_dialog);
        taskId = getIntent().getIntExtra(IntentDataKey.MY_TASK, 0);
        if (taskId == 0)
            finish();
        //ratingBar = findViewById(R.id.rating_bar);
        etComment = findViewById(R.id.et_comment);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1)
                    ratingBar.setRating(1);
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(this);
                RequestAndResponse.rateTask(this, taskId, etComment.getText().toString(), new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(RateUserDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.MY_TASK, taskId);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(RateUserDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                    }
                });
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
