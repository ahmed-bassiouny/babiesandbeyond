package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.MessageAdmin;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class NewMessageToAdminActivity extends AppCompatActivity {

    private EditText etMessage;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message_to_admin);

        etMessage = findViewById(R.id.et_message);
        progress = findViewById(R.id.progress);
        findViewById(R.id.btn_new_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMessage.getText().toString().trim().isEmpty())
                    return;
                progress.setVisibility(View.VISIBLE);
                RequestAndResponse.sendMessageToAdmin(NewMessageToAdminActivity.this, etMessage.getText().toString(),
                        new BaseResponseInterface<MessageAdmin>() {
                            @Override
                            public void onSuccess(MessageAdmin messageAdmin) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(IntentDataKey.MESSAGE, messageAdmin);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }

                            @Override
                            public void onFailed(String errorMessage) {
                                progress.setVisibility(View.INVISIBLE);
                                Toast.makeText(NewMessageToAdminActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
