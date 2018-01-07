package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class CreateGroupActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etGroupName, etGroupDescription;
    private Button btnCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.create_group);
        findViewById();
        onCLick();
    }

    private void onCLick() {
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etGroupName.getText().toString().trim().isEmpty()){
                    etGroupName.setError(getString(R.string.fill_data));
                }else if(etGroupDescription.getText().toString().trim().isEmpty()){
                    etGroupDescription.setError(getString(R.string.fill_data));
                }else {
                    // save data and create group
                }
            }
        });
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUploadPhoto = findViewById(R.id.tv_upload_photo);
        etGroupName = findViewById(R.id.et_group_name);
        etGroupDescription = findViewById(R.id.et_group_description);
        btnCreateGroup = findViewById(R.id.btn_create_group);
    }
}
