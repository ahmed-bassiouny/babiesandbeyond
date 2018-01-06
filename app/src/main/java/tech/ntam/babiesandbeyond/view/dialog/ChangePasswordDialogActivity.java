package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;

public class ChangePasswordDialogActivity extends AppCompatActivity {

    private EditText etPassword, etConfirmPassword;
    private Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_dialog);
        findViewById();
        onClick();
    }

    private void onClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().length()<6){
                    etPassword.setError(getString(R.string.invalid_password));
                }else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    etConfirmPassword.setError(getString(R.string.invalid_confirm_password));
                }else {
                    MyDialog.showMyDialog(ChangePasswordDialogActivity.this);
                    RequestAndResponse.updatePassword(ChangePasswordDialogActivity.this, etPassword.getText().toString(), new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            MyDialog.dismissMyDialog();
                            Toast.makeText(ChangePasswordDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            MyDialog.dismissMyDialog();
                            Toast.makeText(ChangePasswordDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViewById() {
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}
