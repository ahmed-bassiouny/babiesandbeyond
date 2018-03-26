package tech.ntam.adminapp.view.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.imagepicker.ImagePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Client;
import tech.ntam.adminapp.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.ProcessInterface;

public class AddClientActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etBirthday;
    private Button btnCreateUser;
    private String photo = "";
    private Calendar now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        findView();
        onCLick();
    }

    private void onCLick() {
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(AddClientActivity.this, "Select your image:");

            }
        });
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(getFragmentManager(), etBirthday);
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photo.isEmpty()) {
                    Toast.makeText(AddClientActivity.this, "Please Select Photo", Toast.LENGTH_SHORT).show();
                } else if (etName.getText().toString().trim().isEmpty()) {
                    etName.setError(getString(R.string.invalid_name));
                } else if (etPhone.getText().toString().trim().isEmpty()) {
                    etPhone.setError(getString(R.string.invalid_phone));
                } else if (etPhone.getText().toString().trim().isEmpty()) {
                    etPhone.setError(getString(R.string.invalid_phone));
                } else if (!Utils.validateEmail(etEmail.getText().toString().trim())) {
                    etEmail.setError(getString(R.string.invalid_email));
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError(getString(R.string.invalid_password));
                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    etConfirmPassword.setError(getString(R.string.invalid_confirm_password));
                } else if (etBirthday.getText().toString().isEmpty()) {
                    etBirthday.setError("Please Enter Birthday");
                } else {
                    createClient();
                }
            }
        });
    }

    private void findView() {
        setupToolbar(getString(R.string.add_client));
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUploadPhoto = findViewById(R.id.tv_upload_photo);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnCreateUser = findViewById(R.id.btn_create_user);
        etBirthday = findViewById(R.id.et_birthday);
        now = Calendar.getInstance();

    }

    private void createClient() {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestAndResponse.addClient(AddClientActivity.this,
                        etName.getText().toString(),
                        etEmail.getText().toString(),
                        etPhone.getText().toString(),
                        photo, etBirthday.getText().toString(),
                        etPassword.getText().toString(), new BaseResponseInterface<Client>() {
                            @Override
                            public void onSuccess(Client client) {
                                Intent intent = new Intent();
                                intent.putExtra(IntentDataKey.CLIENT, client);
                                setResult(Activity.RESULT_OK, intent);
                                dialog.dismissMyDialog();
                                finish();
                            }

                            @Override
                            public void onFailed(String errorMessage) {
                                final String error = errorMessage;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddClientActivity.this, error, Toast.LENGTH_SHORT).show();
                                        dialog.dismissMyDialog();
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bitmap == null)
            return;
        ivProfilePhoto.setImageBitmap(bitmap);
        Utils.convertImageFromBitmapToStringBase64(bitmap, new ProcessInterface() {
            @Override
            public void completed(String item) {
                photo = item;
            }
        });

    }

    public void showDate(final FragmentManager fragmentManager, final EditText editText) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + getValueDateDigit(monthOfYear + 1) + "-" + getValueDateDigit(dayOfMonth);
                        etBirthday.setText(date);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setAccentColor(getResources()
                .getColor(R.color.colorPrimary));
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    private String getValueDateDigit(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
    }

}
