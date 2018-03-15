package tech.ntam.babiesandbeyond.view.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Terms;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class ContactActivity extends MyToolbar {

    private EditText etEmail;
    private Spinner spReason;
    private EditText etSubject;
    private EditText etMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setupToolbar(this, false, true, false);
        tvTitle.setText(getString(R.string.contact_us));
        findView();
    }

    private void findView() {
        etEmail = findViewById(R.id.et_email);
        spReason = findViewById(R.id.sp_reason);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().trim().isEmpty()){
                    etEmail.setError(getString(R.string.invalid_email));
                }else if(spReason.getSelectedItemPosition()==0){
                    Toast.makeText(ContactActivity.this, "Please Select Reason", Toast.LENGTH_SHORT).show();
                }else if(etSubject.getText().toString().trim().isEmpty()){
                    etSubject.setError("Please Enter Subject");
                }else if(etMessage.getText().toString().trim().isEmpty()){
                    etMessage.setError("Please Enter Message");
                }else {
                    sendContactUs();
                }
            }
        });
    }

    private void sendContactUs(){
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.sendContactUs(this, etEmail.getText().toString(),
                etSubject.getText().toString(), spReason.getSelectedItem().toString(),
                etMessage.getText().toString(), new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        myDialog.dismissMyDialog();
                        Toast.makeText(ContactActivity.this, s, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        myDialog.dismissMyDialog();
                        Toast.makeText(ContactActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
