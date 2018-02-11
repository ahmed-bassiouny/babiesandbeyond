package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class UserForgetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        etEmail = findViewById(R.id.et_email);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }
    private void sendEmail(){
        if(!Utils.validate(etEmail.getText().toString())){
            etEmail.setError(getString(R.string.invalid_email));
        }else {
            final MyDialog myDialog = new MyDialog();
            myDialog.showMyDialog(this);
            RequestAndResponse.forgetPassword(etEmail.getText().toString(), new BaseResponseInterface<String>() {
                @Override
                public void onSuccess(String s) {
                    myDialog.dismissMyDialog();
                    Toast.makeText(UserForgetPasswordActivity.this, s, Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailed(String errorMessage) {
                    myDialog.dismissMyDialog();
                    Toast.makeText(UserForgetPasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
