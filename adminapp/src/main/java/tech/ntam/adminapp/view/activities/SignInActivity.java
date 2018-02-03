package tech.ntam.adminapp.view.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.User;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById();
        checkIfUserLoggedIn();
    }

    private void findViewById() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError(getString(R.string.invalid_email));
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError(getString(R.string.invalid_password));
                } else {
                    // valid email and password
                    // call sign in method Controller
                    SignIn(etEmail.getText().toString()
                            , etPassword.getText().toString());
                }
            }
        });
    }

    public void SignIn(String email, String password) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.login(this,email, password, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                myDialog.dismissMyDialog();
                if (user.getUserTypeId().equals(User.ADMIN)) {
                    // save user information in sharedpref
                    UserSharedPref.setUserInfo(SignInActivity.this, user.getUser_token(),user.getEmail(),user.getId(),user.getName(),user.getPhoto(),user.getPhone(),false);
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(SignInActivity.this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkIfUserLoggedIn() {
         if (!UserSharedPref.getEmail(this).isEmpty()) {
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();
        }
    }
}
