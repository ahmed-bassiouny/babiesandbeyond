package tech.ntam.babiesandbeyond.view.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.SharedPref;
import tech.ntam.mylibrary.UserSharedPref;

public class ActiveAccountActivity extends AppCompatActivity {

    private TextInputEditText etActiveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_account);
        etActiveAccount = findViewById(R.id.et_active_account);
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

    }
    private void resendCode(){
        // todo save code in shared
    }
}
