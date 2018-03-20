package tech.ntam.adminapp.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.Client;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

public class ClientActivity extends AppCompatActivity {

    private CircleImageView ivProfilePhoto;
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etBirthday;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        findView();
        setData();
    }

    private void setData() {
        client = getIntent().getParcelableExtra(IntentDataKey.CLIENT);
        if(client == null)
            finish();
        if(!client.getPhoto().isEmpty())
            Utils.MyGlide(this,ivProfilePhoto,client.getPhoto());
        etName.setText(client.getName());
        etEmail.setText(client.getEmail());
        etPhone.setText(client.getPhone());
        etBirthday.setText(client.getBirthday());
    }

    private void findView() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etBirthday = findViewById(R.id.et_birthday);
    }
}
