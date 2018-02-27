package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.SharedPref;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class ActiveAccountActivity extends AppCompatActivity {

    private TextInputEditText etActiveAccount;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_account);
        etActiveAccount = findViewById(R.id.et_active_account);
        hint = findViewById(R.id.hint);
        hint.setText(getString(R.string.enter_code)+" '"+UserSharedPref.getEmail(this)+"'");
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etActiveAccount.getText().toString().
                        equals(UserSharedPref.getActivationCode(ActiveAccountActivity.this))){
                    activeAccount();
                }else {
                    etActiveAccount.setError(getString(R.string.invalid_code));
                }
            }
        });
        findViewById(R.id.resend_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();
            }
        });
    }

    private void activeAccount(){
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.activeAccount(this, new BaseResponseInterface() {
            @Override
            public void onSuccess(Object o) {
                UserSharedPref.setIsActive(ActiveAccountActivity.this);
                startActivity(new Intent(ActiveAccountActivity.this,UserHomeActivity.class));
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                dialog.dismissMyDialog();
                Toast.makeText(ActiveAccountActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendCode(){
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.resendCode(this, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(ActiveAccountActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                UserSharedPref.setActiveCode(ActiveAccountActivity.this,s);
                dialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                dialog.dismissMyDialog();
                Toast.makeText(ActiveAccountActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}